package com.simbest.cores.utils;

import java.beans.PropertyDescriptor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.ParameterizedType;
import java.util.Arrays;
import java.util.Collection;
import java.util.Map;
import java.util.Set;

import javax.persistence.Id;

import org.apache.commons.beanutils.PropertyUtils;

import com.google.common.collect.Sets;
import com.simbest.cores.app.model.ProcessModel;
import com.simbest.cores.app.service.impl.ProcessAdapterService;
import com.simbest.cores.exceptions.Exceptions;
import com.simbest.cores.model.GenericModel;
import com.simbest.cores.utils.annotations.ProcessProperty;
import com.simbest.cores.utils.annotations.Unique;

public class ObjectUtil extends org.apache.commons.lang.ObjectUtils {

	/**
	 * 判断对象的所有属性是否均为空
	 * 
	 * @param obj
	 * @return
	 */
	public static boolean isEmpty(GenericModel<?> obj) {
		for (PropertyDescriptor pd : PropertyUtils.getPropertyDescriptors(obj)) {
			try{
				if (!pd.getName().equals("class")&& pd.getReadMethod()!=null && !pd.getReadMethod().getName().equals("isEmpty")) {
					try {
						Field f = getIndicateField(obj, pd.getName());
						if (!f.isAnnotationPresent(javax.persistence.Transient.class)) {
							Object value = pd.getReadMethod().invoke(obj);
							if (value instanceof Collection) {
								if (((Collection<?>) value).size() != 0)
									return false;
							} else if (value instanceof Map) {
								if (((Map<?, ?>) value).size() != 0)
									return false;
							} else if (value != null)
								return false;
						}
					} catch (IllegalAccessException | InvocationTargetException e) {
						return false;
					}
				}
			}catch(Exception e){
				Exceptions.printException(e);
			}
		}
		return true;
	}

	/**
	 * 获取ProcessAdapterService子类中持久化对象的主键Id字段
	 * 
	 * @param clazz
	 * @return
	 */
	public static Field getProcessServiceIdField(
			@SuppressWarnings("rawtypes") Class<? extends ProcessAdapterService> clazz) {
		Field id = null;
		Class<?> processClass = (Class<?>) ((ParameterizedType) clazz
				.getGenericSuperclass()).getActualTypeArguments()[0];
		for (Field field : processClass.getSuperclass().getDeclaredFields()) {
			if (field.isAnnotationPresent(Id.class)) {
				id = field;
				break;
			}
		}
		return id;
	}

	/**
	 * 获取持久化对象的主键Id字段
	 * 
	 * @param obj
	 * @return
	 */
	public static Field getIdField(GenericModel<?> obj) {
		Field id = null;
		for (Field field : getAllFields(obj.getClass())) {
			if (field.isAnnotationPresent(Id.class)) {
				id = field;
				break;
			}
		}
		return id;
	}

	/**
	 * 获取持久化对象的唯一性字段
	 * 
	 * @param obj
	 * @return
	 */
	public static Field getUniqueField(GenericModel<?> obj) {
		Field unique = null;
		for (Field field : getAllFields(obj.getClass())) {
			if (field.isAnnotationPresent(Unique.class)) {
				unique = field;
				break;
			}
		}
		return unique;
	}

	/**
	 * 获取指定字段的Field
	 * @param obj
	 * @param fieldName
	 * @return
	 */
	public static Field getIndicateField(GenericModel<?> obj, String fieldName) {
		Field indicateField = null;
		for (Field field : getAllFields(obj.getClass())) {
			if (field.getName().equals(fieldName)) {
				indicateField = field;
				break;
			}
		}
		return indicateField;
	}
	
	public static Collection<Field> getProcessFields(ProcessModel<?> process) {
		Set<Field> fields = Sets.newHashSet();
		for (Field field : getAllFields(process.getClass())) {
			if (field.isAnnotationPresent(ProcessProperty.class)) {
				fields.add(field);
			}
		}
		return fields;
	}

	public static String[] getProcessFieldNames(ProcessModel<?> process) {
		Set<String> fieldNames = Sets.newHashSet();
		for (Field field : getAllFields(process.getClass())) {
			if (field.isAnnotationPresent(ProcessProperty.class)) {
				fieldNames.add(field.getName());
			}
		}
		return fieldNames.toArray(new String[fieldNames.size()]);
	}
	
	/**
	 * 返回所有字段
	 */
	public static Field[] getAllFields(Class<?> clazz) {
		Collection<Class<?>> classes = getAllSuperClasses(clazz);
		classes.add(clazz);
		return getAllFields(classes);
	}

	/**
	 * 返回所有字段
	 */
	private static Field[] getAllFields(Collection<Class<?>> classes) {
		Set<Field> fields = Sets.newHashSet();
		for (Class<?> clazz : classes) {
			fields.addAll(Arrays.asList(clazz.getDeclaredFields()));
		}
		return fields.toArray(new Field[fields.size()]);
	}

	/**
	 * 返回所有超类
	 */
	public static Collection<Class<?>> getAllSuperClasses(Class<?> clazz) {
		Set<Class<?>> classes = Sets.newHashSet();
		Class<?> superclass = clazz.getSuperclass();
		while (superclass != null) {
			classes.add(superclass);
			superclass = superclass.getSuperclass();
		}

		return classes;
	}
}
