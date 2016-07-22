package ua.com.as.parsing;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ua.com.as.annotation.Path;
import ua.com.as.exception.ScanningException;
import ua.com.as.jaxb.Controller;
import ua.com.as.jaxb.Rest;
import ua.com.as.util.CustomClassLoader;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Method;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Enumeration;
import java.util.List;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

import static ua.com.as.util.ReflectionHelper.createClass;
import static ua.com.as.util.ReflectionHelper.getAnnotatedMethods;
import static ua.com.as.util.ReflectionHelper.isController;

/**
 * <p>Scans packages for <code>classes</code>
 * which have {@link ua.com.as.annotation.Controller} annotation,
 * find there <code>methods</code> which have {@link ua.com.as.annotation.Path}
 * annotation and group them into {@link Controller} entities.
 *
 * @see Controller
 */
public class PackageScannerImpl implements PackageScanner {
    private static final Logger LOG = LoggerFactory.getLogger(PackageScanner.class);
    private static final String CLASS_FILE_SUFFIX = ".class";
    private static final String JAR_URL_PREFIX = "jar:";
    private static final String DOT_DELIMITER = ".";
    private static final String SLASH_DELIMITER = "/";
    private CustomClassLoader customClassLoader;

    /**
     * <p>Base overridden method for scanning packages.
     *
     * @param packagesToScan packages to scan
     * @return list of <code>controllers</code>, in which each <code>controller</code>
     * stores specified <code>url path, class name, httpMethods and methodsToInvoke</code> for
     * successfully invocation
     */
    @Override
    public List<Controller> scan(List<String> packagesToScan) {
        LOG.debug("Scanning started");
        customClassLoader = new CustomClassLoader();
        List<Controller> scannedControllers = new ArrayList<>();
        for (String packagee : packagesToScan) {
            assert packagee != null;
            packagee = packagee.trim();
            scannedControllers.addAll(processPackage(packagee));
        }
        customClassLoader = null;
        LOG.debug("Scanning finished successfully\n");
        return scannedControllers;
    }

    /**
     * <p>First phase of scanning package.
     * <p>Checking package and preparing directory for further work.
     *
     * @param packageToScan package to scan
     * @return list of <code>controllers</code>, in which each <code>controller</code>
     * stores specified <code>url path, class name, httpMethods and methodsToInvoke</code> for
     * successfully invocation
     */
    @SuppressWarnings("ConstantConditions")
    private List<Controller> processPackage(String packageToScan) {
        LOG.debug("Scan package {}", packageToScan);

        String directoryPath = packageToScan.replace(DOT_DELIMITER, SLASH_DELIMITER);
        URL scannedUrl = Thread.currentThread().getContextClassLoader().getResource(directoryPath);
        assert scannedUrl != null;

        String urlString = scannedUrl.toString();

        if (!urlString.startsWith(JAR_URL_PREFIX)) {
            File directoryToScan;
            try {
                directoryToScan = new File(scannedUrl.toURI());
            } catch (URISyntaxException e) {
                throw new ScanningException("creating file error", e);
            }

            List<Controller> controllers = new ArrayList<>();
            Arrays.stream(directoryToScan.listFiles())
                    .forEach(file -> controllers.addAll(processDirectory(file, packageToScan)));
            LOG.debug("Package {} scanned successfully", packageToScan);
            return controllers;
        } else {
            String path = PackageScannerImpl.class
                    .getProtectionDomain()
                    .getCodeSource()
                    .getLocation()
                    .getPath();
            return processJar(path);
        }
    }

    /**
     * <p>Second phase of scanning package.
     * <p>Checks if current file is directory or not, if it is -
     * scan all sub-packages recursive, otherwise call method {@link #createClassIfController}
     * to update path storage.
     *
     * @param directory      file to check if it is directory or not
     * @param currentPackage package to scan
     * @return list of <code>controllers</code>, in which each <code>controller</code>
     * stores specified <code>url path, class name, httpMethods and methodsToInvoke</code> for
     * successfully invocation
     */
    @SuppressWarnings("ConstantConditions")
    private List<Controller> processDirectory(File directory, String currentPackage) {
        List<Controller> controllers = new ArrayList<>();
        String pathToClass = currentPackage + DOT_DELIMITER + directory.getName();

        if (directory.listFiles() != null && directory.isDirectory()) {
            Arrays.stream(directory.listFiles())
                    .forEach(child -> controllers.addAll(processDirectory(child, pathToClass)));
        } else if (pathToClass.endsWith(CLASS_FILE_SUFFIX)) {
            Controller controller = createClassIfController(pathToClass);
            if (controller != null) {
                controllers.add(controller);
            }
        }

        return controllers;
    }

    /**
     * <p>Second phase of scanning package.
     * <p>If source is <code>.jar</code> file, then this method is used to scan packages.
     *
     * @param pathToJar path to jar file.
     * @return list of <code>controllers</code>, in which each <code>controller</code>
     * stores specified <code>url path, class name, httpMethods and methodsToInvoke</code> for
     * successfully invocation
     */
    private List<Controller> processJar(String pathToJar) {
        try {
            File jarFile = new File(pathToJar);
            try (JarFile jar = new JarFile(jarFile)) {
                return getControllerFromJar(jar);
            }
        } catch (IOException e) {
            throw new ScanningException("jar file reading error", e);
        }
    }

    /**
     * <p>Get list of Controllers from specified <code>jar file</code>.
     *
     * @param jar jar file to be scanned.
     * @return list of Controllers from specified <code>jar file</code>.
     */
    private List<Controller> getControllerFromJar(JarFile jar) {
        List<Controller> controllers = new ArrayList<>();
        Enumeration<JarEntry> entries = jar.entries();
        while (entries.hasMoreElements()) {
            final String pathToClass = entries.nextElement().getName();
            if (pathToClass.endsWith(CLASS_FILE_SUFFIX)) {
                Controller controller = createClassIfController(pathToClass);
                if (controller != null) {
                    controllers.add(controller);
                }
            }
        }
        return controllers;
    }

    /**
     * <p>Create object of class, if class is instance of <code>Controller class</code>.
     * <p>Checks for methods with annotation {@link Path}
     * and creating {@link Controller} entities.
     * <p>If object of class is not instance of <code>Controller class</code> returns null.
     *
     * @param pathToClass path to current class
     * @return Object of class, if class is instance of <code>Controller class</code>.
     * If object of class is not instance of <code>Controller class</code> returns null.
     */
    private Controller createClassIfController(String pathToClass) {
        int endIndex = pathToClass.length() - CLASS_FILE_SUFFIX.length();
        String fullClassName = pathToClass.substring(0, endIndex);

        return isController(createClass(fullClassName, customClassLoader))
                ? createClassIfHasPathAnnotatedMethods(fullClassName) : null;
    }

    /**
     * <p>Create object of class Controller, if that controller has methods marked with <code>Path</code> annotation.
     * If instance of class Controller has not methods marked with <code>Path</code> annotation returns null.
     *
     * @param fullClassName full class name of controller
     * @return Object of class Controller, if that controller has methods marked with <code>Path</code> annotation.
     * If instance of class Controller has not methods marked with <code>Path</code> annotation returns null.
     */
    private Controller createClassIfHasPathAnnotatedMethods(String fullClassName) {
        Class<?> clazz = createClass(fullClassName, getClass().getClassLoader());
        List<Method> pathAnnotatedMethods = getAnnotatedMethods(clazz, Path.class);

        return !pathAnnotatedMethods.isEmpty() ? getController(fullClassName, pathAnnotatedMethods) : null;
    }

    /**
     * <p>Returns object of class controller.
     *
     * @param fullClassName        full class name of controller.
     * @param pathAnnotatedMethods list of methods marked with <code>Path</code> annotation.
     * @return object of class Controller
     */
    private Controller getController(String fullClassName, List<Method> pathAnnotatedMethods) {
        Controller controllerEntity = new Controller();
        List<Rest> rests = new ArrayList<>();
        controllerEntity.setFullClassName(fullClassName);

        for (Method pathAnnotatedMethod : pathAnnotatedMethods) {
            rests.addAll(getRestsByMethod(pathAnnotatedMethod));
        }

        controllerEntity.setRests(rests);
        return controllerEntity;
    }

    /**
     * <p>Create list of object of Rest class by specified method which is marked with <code>Path</code> annotation.
     *
     * @param pathAnnotatedMethod method marked with <code>Path</code> annotation.
     * @return List of object of Rest class by specified method which is marked with <code>Path</code> annotation.
     */
    private List<Rest> getRestsByMethod(Method pathAnnotatedMethod) {
        List<Rest> rests = new ArrayList<>();
        ua.com.as.jaxb.Method methodEntity = new ua.com.as.jaxb.Method();
        methodEntity.setName(pathAnnotatedMethod.getName());

        String urlPath = pathAnnotatedMethod.getAnnotation(Path.class).value();
        String[] httpMethodNames = pathAnnotatedMethod.getAnnotation(Path.class).methods();

        for (String httpMethodName : httpMethodNames) {
            rests.add(createRest(urlPath, httpMethodName, methodEntity));
        }

        return rests;
    }

    /**
     * <p>Returns object of Rest class by specified parameters: <code>urlPath, httpMethodName, methodEntity</code>.
     *
     * @param urlPath        path of client's query.
     * @param httpMethodName http method type.
     * @param methodEntity   method which will be invoked in controller.
     * @return object of Rest class
     */
    private Rest createRest(String urlPath, String httpMethodName, ua.com.as.jaxb.Method methodEntity) {
        Rest rest = new Rest();
        rest.setPath(urlPath);
        rest.setAction(httpMethodName);
        rest.setMethod(methodEntity);
        return rest;
    }
}
