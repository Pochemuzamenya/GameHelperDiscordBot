package org.filatov.service;

import org.filatov.customcl.CustomClassLoader;
import org.filatov.model.springbean.BeanMD;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.GenericBeanDefinition;
import org.springframework.context.support.GenericApplicationContext;
import org.springframework.stereotype.Service;

@Service
public class BeanRegistrationService {

    @Autowired
    private GenericApplicationContext context;

    @Autowired
    private CustomClassLoader ccl;

    public String regBean(BeanMD beanMD) {
        Class<?> beanClass = ccl.findClass(beanMD.getBeanClassName());

        BeanDefinitionRegistry beanFactory = (BeanDefinitionRegistry) context.getBeanFactory();

        GenericBeanDefinition beanDefinition = new GenericBeanDefinition();
        beanDefinition.setScope(BeanDefinition.SCOPE_SINGLETON);
        beanDefinition.setBeanClass(beanClass);
        beanFactory.registerBeanDefinition(beanMD.getBeanName(), beanDefinition);

        Object contextBean = context.getBean(beanMD.getBeanName());
        System.out.println(contextBean);
        return "registered";
    }
}
