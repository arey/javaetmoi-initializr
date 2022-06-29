package com.javaetmoi.initializr.generator.openapi;

import com.javaetmoi.initializr.generator.core.AbstractRemoveDependencyCustomizer;


import static com.javaetmoi.initializr.generator.core.InitializrConstants.DEPENDENCY_OPENAPI;

/**
 * Remove the fake openapi dependency from the Maven build.
 */
class RemoveOpenAPIDependencyCustomizer extends AbstractRemoveDependencyCustomizer {

    protected RemoveOpenAPIDependencyCustomizer() {
        super(DEPENDENCY_OPENAPI);
    }

}
