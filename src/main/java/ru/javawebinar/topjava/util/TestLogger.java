package ru.javawebinar.topjava.util;

import org.junit.rules.TestRule;
import org.junit.runner.Description;
import org.junit.runners.model.Statement;
import ru.javawebinar.topjava.LoggerWrapper;

/**
 * Created by Dimka on 27.12.2015.
 */
public class TestLogger implements TestRule {
    private LoggerWrapper logger;

    public LoggerWrapper getLogger() {
        return this.logger;
    }

    @Override
    public Statement apply(final Statement base, final Description description) {
        return new Statement() {
            @Override
            public void evaluate() throws Throwable {
                logger = LoggerWrapper.get(description.getTestClass());
                try {
                    base.evaluate();
                } finally {
                    logger = null;
                }
            }
        };
    }
}