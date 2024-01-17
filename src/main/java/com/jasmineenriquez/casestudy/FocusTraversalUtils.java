
package com.jasmineenriquez.casestudy;

import java.awt.Component;
import java.awt.Container;
import java.awt.FocusTraversalPolicy;

public class FocusTraversalUtils {
    public static void setCustomFocusTraversalPolicy(Container container, Component[] components) {
        FocusTraversalPolicy customPolicy = new FocusTraversalPolicy() {
            @Override
            public Component getComponentAfter(Container container, Component component) {
                int index = (getIndex(component) + 1) % components.length;
                return components[index];
            }
            
            @Override
            public Component getComponentBefore(Container container, Component component) {
                int index = getIndex(component) - 1;
                if (index < 0) {
                    index = components.length - 1;
                }
                return components[index];
            }
            
            @Override
            public Component getFirstComponent(Container container) {
                return components[0];
            }
            
            @Override
            public Component getLastComponent(Container container) {
                return components[components.length - 1];
            }
            
            @Override
            public Component getDefaultComponent(Container container) {
                return getFirstComponent(container);
            }
            
            private int getIndex(Component component) {
                for (int i = 0; i < components.length; i++) {
                    if (components[i] == component) {
                        return i;
                    }
                }
                return -1;
            }
        };
        
        container.setFocusTraversalPolicyProvider(true);
        container.setFocusTraversalPolicy(customPolicy);
    }
}