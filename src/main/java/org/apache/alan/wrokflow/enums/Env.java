package org.apache.alan.wrokflow.enums;

/**
 */
public enum Env {
    local,
    dev,
    test,
    uat,
    prod;

    private Env() {
    }

    private Env valueOfName(String name) {
        Env[] envs = values();
        for (Env env : envs) {
            if (env.name().equals(name)){
                return env;
            }
        }
        return null;
    }
}

