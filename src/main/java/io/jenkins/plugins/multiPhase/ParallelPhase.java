package io.jenkins.plugins.multiPhase;

import groovy.lang.Binding;
import hudson.Extension;
import org.jenkinsci.plugins.workflow.cps.CpsScript;
import org.jenkinsci.plugins.workflow.cps.GlobalVariable;


@Extension public class ParallelPhase extends GlobalVariable {

    @Override public String getName() {
        return "parallelPhase";
    }

    @Override public Object getValue(CpsScript script) throws Exception {
        Binding binding = script.getBinding();
        Object parallelPhase;
        if (binding.hasVariable(getName())) {
            parallelPhase = binding.getVariable(getName());
        } else {
            // Note that if this were a method rather than a constructor, we would need to mark it @NonCPS lest it throw CpsCallableInvocation.
            parallelPhase = script.getClass().getClassLoader().loadClass("io.jenkins.plugins.multiPhase.parallelPhase").getConstructor(CpsScript.class).newInstance(script);
            binding.setVariable(getName(), parallelPhase);
        }
        return parallelPhase;
    }

}
