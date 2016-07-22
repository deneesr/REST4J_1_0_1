package ua.com.as.service;

import org.junit.Test;
import ua.com.as.exception.InvocationException;
import ua.com.as.util.ReflectionHelper;

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;

import static org.junit.Assert.assertTrue;

/**
 *
 */
@SuppressWarnings("all")
public class ReflectionHelperTest {
    @Test(expected = InvocationException.class)
    public void testCreateClassNotFound() {
        ReflectionHelper.createClass("", getClass().getClassLoader());
    }

    @Test(expected = InvocationException.class)
    public void testCreateMethodNotFound() {
        Class clazz = ReflectionHelperTest.class;
        ReflectionHelper.createMethod(clazz, "");
    }

    @Test(expected = InvocationException.class)
    public void testInvokeError() throws NoSuchMethodException {
        Class clazz = ReflectionHelperTest.class;
        Method method = clazz.getMethod("testInvokeError");
        ReflectionHelper.invoke(clazz, method);
    }

    @Test(expected = AssertionError.class)
    public void testInvokeNullClass() {
        ReflectionHelper.invoke(getClass(), null);
    }

    @Test(expected = AssertionError.class)
    public void testInvokeNullMethod() throws NoSuchMethodException {
        ReflectionHelper.invoke(null, getClass().getDeclaredMethod("testInvokeNullMethod"));
    }

    @Test
    public void testConstructor() throws Exception {
        Constructor constructor = ReflectionHelper.class.getDeclaredConstructor();
        assertTrue(Modifier.isPrivate(constructor.getModifiers()));
        constructor.setAccessible(true);
        constructor.newInstance();
    }
}
