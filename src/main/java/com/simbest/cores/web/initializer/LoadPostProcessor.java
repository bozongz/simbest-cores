package com.simbest.cores.web.initializer;

import java.io.*;

import org.apache.commons.io.FileUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.stereotype.Component;

import com.simbest.cores.exceptions.Exceptions;
import com.simbest.cores.utils.configs.CoreConfig;

@Component
public class LoadPostProcessor implements BeanPostProcessor {
	public static transient final Log log = LogFactory.getLog(LoadPostProcessor.class);
	private static File beanFile = new File(System.getProperty("user.dir")+"/LoadPostProcessor.txt");

	@Autowired
	protected CoreConfig config;
	
	static {
		try {
			FileUtils.forceDeleteOnExit(beanFile);
			FileUtils.touch(beanFile);
		} catch (IOException e) {
			log.error(Exceptions.getStackTraceAsString(e));
		}
	}

	@Override
	public Object postProcessAfterInitialization(Object bean, String beanName)
			throws BeansException {
		return bean;
	}

	@Override
	public Object postProcessBeforeInitialization(Object bean, String beanName)
			throws BeansException {
		if(Boolean.valueOf(config.getValue("app.debug"))){
			if (beanFile.exists()) {
                OutputStreamWriter writer = null;
				try {
                    FileOutputStream fileStream = new FileOutputStream(beanFile);
                    writer = new OutputStreamWriter(fileStream, "UTF-8");
					writer.write(beanName+"\r\n");
				}catch (IOException e) {
				}finally {
                    try {
                        if (null != writer)
                            writer.close();
                    } catch (IOException e) {
                    }
                }
            }
		}
		return bean;
	}
}
