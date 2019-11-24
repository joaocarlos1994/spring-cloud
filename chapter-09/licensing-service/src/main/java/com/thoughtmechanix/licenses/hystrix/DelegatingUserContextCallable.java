package com.thoughtmechanix.licenses.hystrix;

import com.thoughtmechanix.licenses.utils.UserContext;
import com.thoughtmechanix.licenses.utils.UserContextHolder;
import java.util.concurrent.Callable;

public class DelegatingUserContextCallable<V> implements Callable<V> {

    private final Callable<V> delegate;
    private UserContext originalUserContext;

    /**
     * Custom Callable class will be passed the original Callable
     * class that will invoke your Hystrix protected code and
     * UserContext coming in from the parent thread
     * */
    public DelegatingUserContextCallable(Callable<V> delegate,
                                         UserContext userContext) {
        this.delegate = delegate;
        this.originalUserContext = userContext;
    }

    /**
     * The call() function is invoked before
     * the method protected by the @HystrixCommand annotation.
     * */
    public V call() throws Exception {
        //The UserContext is set. The ThreadLocal variable that stores
        //the UserContext is associated with the thread running the
        //Hystrix protected method.
        UserContextHolder.setContext(originalUserContext);
        try {
            //Once the UserContext is set invoke the call() method on the Hystrix protected
            //method; for instance, your LicenseServer.getLicenseByOrg() method
            return delegate.call();
        }
        finally {
            this.originalUserContext = null;
        }
    }

    public static <V> Callable<V> create(Callable<V> delegate,
                                         UserContext userContext) {
        return new DelegatingUserContextCallable<V>(delegate, userContext);
    }
}
