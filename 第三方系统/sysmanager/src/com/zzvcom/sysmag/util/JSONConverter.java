/*
 * Copyright 2002-2005 the original author or authors.
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *      http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.zzvcom.sysmag.util;

import java.beans.BeanInfo;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Array;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class JSONConverter {
	private static JSONConverter coverter = null;

	public static synchronized JSONConverter getInstance() {
		if (coverter == null) {
			coverter = new JSONConverter();
		}
		return coverter;

	}

	/**
	 * to avoid the cycling marshall
	 */
	// private Set m_marshallObjectSet;
	public JSONConverter() {
		// m_marshallObjectSet = new HashSet();
	}
	/**
     * ʱ��ת��Ϊ�ַ�
     *
     * @param date
     * @param format
     * @return s
     */
    private String dateFormat(Date date,String format) {
        return new SimpleDateFormat(format).format(date);
    }
    /**
     * �ַ�ת��Ϊ���������
     *
     * @param src
     * @return date
     */
    public static Date parseDate(String src) {
    	String format="";
        if(src.matches("\\d{4}-\\d{1,2}-\\d{1,2}")){
        	format="yyyy-MM-dd";
        }else if(src.matches("\\d{4}-\\d{1,2}-\\d{1,2} \\d{1,2}:\\d{1,2}:\\d{1,2}")){
        	format="yyyy-MM-dd HH:mm:ss";
        }
        SimpleDateFormat sf = new SimpleDateFormat(format);
        try {
            return sf.parse(src);
        } catch (ParseException e) {
            return null;
        }
    }
	public Object marshall(Object objectSource,String excludeFieldsList) throws Exception {

		Object jsonResult = new Object();
		if (objectSource != null) {
			// m_marshallObjectSet.add(new Integer(objectSource.hashCode()));
			Class clazz = objectSource.getClass();

			if (objectSource instanceof Number || objectSource instanceof Boolean || objectSource instanceof String || objectSource instanceof Character) {
				jsonResult = objectSource;
			} else if(objectSource instanceof Date){
				if(objectSource!=null)jsonResult=dateFormat((Date) objectSource,"yyyy-MM-dd");
			}else if (clazz.isArray()) {
				JSONArray jsonArray = new JSONArray();
				int length = Array.getLength(objectSource);
				for (int i = 0; i < length; i++) {
					jsonArray.put(marshall(Array.get(objectSource, i),excludeFieldsList));
				}
				jsonResult = jsonArray;
			} else if (objectSource instanceof Collection) {
				Collection theCollection = (Collection) objectSource;
				Iterator iter = theCollection.iterator();
				List tempList = new ArrayList();
				while (iter.hasNext()) {
					Object element = (Object) iter.next();
					tempList.add(marshall(element,excludeFieldsList));
				}
				jsonResult = new JSONArray(tempList);
			} else if (objectSource instanceof Map) {
				Map map = (Map) objectSource;
				JSONObject mapJSONObject = new JSONObject();
				Iterator iter = map.keySet().iterator();
				while (iter.hasNext()) {
					String key = (String) iter.next();
					mapJSONObject.put(key, marshall(map.get(key),excludeFieldsList));
				}
				jsonResult = mapJSONObject;
			} else {
				// for normal beans
				BeanInfo beanInfo = Introspector.getBeanInfo(getOriginalClass(objectSource.getClass()));
				JSONObject beanJSONObject = new JSONObject();
				PropertyDescriptor[] properties = beanInfo.getPropertyDescriptors();
				for (int i = 0; i < properties.length; i++) {
					String propertyName = properties[i].getName();

					if(("," + excludeFieldsList + ",").indexOf("," + propertyName + ",") >=0 || propertyName.equals("class")){
						// beanJSONObject.put("jsonclass",
						// objectSource.getClass()
						// .getName());
					} else {
						Method readMethod = properties[i].getReadMethod();
						if (readMethod != null) {
							Object value = readMethod.invoke(objectSource, new Object[] {});
							// if (value != null &&
							// !m_marshallObjectSet.contains(new
							// Integer(value.hashCode()))) {
							if (value != null) {
								beanJSONObject.put(propertyName, marshall(value,excludeFieldsList));
							}
						}
					}
				}
				jsonResult = beanJSONObject;
			}
		} else {
			jsonResult = null;
		}
		return jsonResult;
	}

	public Object unmarshall(Object jsonSource, Object target) throws Exception {
		if (jsonSource != null) {
			if (target instanceof Integer && jsonSource instanceof String) {
				if (jsonSource == "") {
					target = new Integer(0);
				} else {
					target = new Integer((String) jsonSource);
				}
			} else if (target instanceof Long && jsonSource instanceof String) {
				if (jsonSource == "") {
					target = new Long(0);
				} else {
					target = new Long((String) jsonSource);
				}
			} else if (target instanceof Short && jsonSource instanceof String) {
				if (jsonSource == "") {
					target = new Short((short) 0);
				} else {
					target = new Short((String) jsonSource);
				}
			} else if (target instanceof Byte && jsonSource instanceof String) {
				if (jsonSource == "") {
					target = new Byte((byte) 0);
				} else {
					target = new Byte((String) jsonSource);
				}
			} else if (target instanceof Float && jsonSource instanceof String) {
				if (jsonSource == "") {
					target = new Float(0);
				} else {
					target = new Float((String) jsonSource);
				}
			} else if (target instanceof Double && jsonSource instanceof String) {
				if (jsonSource == "") {
					target = new Double(0);
				} else {
					target = new Double((String) jsonSource);
				}
			} else if (target instanceof Boolean && jsonSource instanceof String) {
				if (jsonSource == "") {
					target = new Boolean(false);
				} else {
					target = new Boolean((String) jsonSource);
				}
			} else if (target instanceof String) {
				target = jsonSource;
			} else if(target instanceof Date){
				System.out.println("target instanceof Date.");
			}else if (target != null && target.getClass().isArray() && jsonSource instanceof JSONArray) {
				JSONArray jsonArray = (JSONArray) jsonSource;
				for (int i = 0; i < jsonArray.length(); i++) {
					Array.set(target, i, getTypedValue(target.getClass().getComponentType(), jsonArray, i));
				}
			} else if (target instanceof Collection && jsonSource instanceof JSONArray) {
				JSONArray jsonArray = (JSONArray) jsonSource;
				for (int i = 0; i < jsonArray.length(); i++) {
					((Collection) target).add(jsonArray.get(i));
				}
			} else if (target instanceof Map && jsonSource instanceof JSONObject) {
				JSONObject mapJSONObject = (JSONObject) jsonSource;
				Iterator keys = mapJSONObject.keys();
				while (keys.hasNext()) {
					String key = (String) keys.next();
					((Map) target).put(key, mapJSONObject.get(key));
				}
			} else if (jsonSource instanceof JSONObject) {
				// for normal beans
				JSONObject beanJSONObject = (JSONObject) jsonSource;
				BeanInfo beanInfo = Introspector.getBeanInfo(target.getClass());
				PropertyDescriptor[] properties = beanInfo.getPropertyDescriptors();
				for (int i = 0; i < properties.length; i++) {
					String propertyName = properties[i].getName();
					Object targetPropertyValue = properties[i].getReadMethod().invoke(target, new Object[] {});
					if (targetPropertyValue == null) {
						//System.out.println(properties[i].getPropertyType().toString());
						if(properties[i].getPropertyType().toString().equals("class java.lang.Long"))targetPropertyValue=new Long(0);
						else if(properties[i].getPropertyType().toString().equals("class java.lang.Integer"))targetPropertyValue=new Integer(0);
						else if(properties[i].getPropertyType().toString().equals("class java.lang.Boolean"))targetPropertyValue=new Boolean(false);
						else if(properties[i].getPropertyType().toString().equals("class java.lang.Double"))targetPropertyValue=new Double(0);
						else if(properties[i].getPropertyType().toString().equals("class java.lang.Float"))targetPropertyValue=new Float(0);
						else if(properties[i].getPropertyType().toString().equals("class java.lang.Short"))targetPropertyValue=new Short((short)0);
						else targetPropertyValue = properties[i].getPropertyType().newInstance();
					}
					if (beanJSONObject.has(propertyName)) {
						Object value = beanJSONObject.get(propertyName);
						
						Object unmarshalledProp = unmarshall(value.toString(), targetPropertyValue);
						if (properties[i].getWriteMethod() != null) {
							properties[i].getWriteMethod().invoke(target, new Object[] { unmarshalledProp });
						}
					}
				}
			}
		}
		return target;
	}
	public Object unObject(Object target,HttpServletRequest request) throws Exception{
		// for normal beans
		BeanInfo beanInfo = Introspector.getBeanInfo(target.getClass());
		PropertyDescriptor[] properties = beanInfo.getPropertyDescriptors();
		for (int i = 0; i < properties.length; i++) {
			String propertyName = properties[i].getName();
			Object targetPropertyValue = properties[i].getReadMethod().invoke(target, new Object[] {});
			if (targetPropertyValue == null) {
				//System.out.println(properties[i].getPropertyType().toString());
				if(properties[i].getPropertyType().toString().equals("class java.lang.Long"))targetPropertyValue=new Long(0);
				else if(properties[i].getPropertyType().toString().equals("class java.lang.Integer"))targetPropertyValue=new Integer(0);
				else if(properties[i].getPropertyType().toString().equals("class java.lang.Boolean"))targetPropertyValue=new Boolean(false);
				else if(properties[i].getPropertyType().toString().equals("class java.lang.Double"))targetPropertyValue=new Double(0);
				else if(properties[i].getPropertyType().toString().equals("class java.lang.Float"))targetPropertyValue=new Float(0);
				else if(properties[i].getPropertyType().toString().equals("class java.lang.Short"))targetPropertyValue=new Short((short)0);
				else targetPropertyValue = properties[i].getPropertyType().newInstance();
			}
			Object unmarshalledProp = unmarshall(propertyName,targetPropertyValue,request);
			if (properties[i].getWriteMethod() != null) {
				properties[i].getWriteMethod().invoke(target, new Object[] { unmarshalledProp });
			}
			
		}
		return target;
		
	}
	public Object unmarshall(String value,Object target,HttpServletRequest request) throws Exception {
		if (value != null) {
			String name=request.getParameter(value);
			if (target instanceof Integer) {
				if (name == null) {
					target = new Integer(0);
				} else {
					target = new Integer((String) name);
				}
			} else if (target instanceof Long) {
				
				if (name == null) {
					target = null;
				} else {
					target = new Long((String) name);
				}
			} else if (target instanceof Short) {
				if (name == "") {
					target = new Short((short) 0);
				} else {
					target = new Short((String) name);
				}
			} else if (target instanceof Byte) {
				if (name == "") {
					target = new Byte((byte) 0);
				} else {
					target = new Byte((String) name);
				}
			} else if (target instanceof Float) {
				if (name == "") {
					target = new Float(0);
				} else {
					target = new Float((String) name);
				}
			} else if (target instanceof Double) {
				if (name == "") {
					target = new Double(0);
				} else {
					target = new Double((String) name);
				}
			} else if (target instanceof Boolean) {
				if (name == "") {
					target = new Boolean(false);
				} else {
					target = new Boolean((String) name);
				}
			} else if (target instanceof String) {
				target = name;
			} else if(target instanceof Date){
				target=parseDate(name);
			}
		}
		return target;
	}
	/**
	 * @param type
	 * @param jsArray
	 * @param index
	 * @return
	 */
	public static Object getTypedValue(Class type, JSONArray jsArray, int index) throws Exception {
		if (jsArray.isNull(index)) {
			return null;
		}

		if (type == int.class) {
			return new Integer(jsArray.getInt(index));
		} else if (type == long.class) {
			return new Long((long) jsArray.getDouble(index));
		} else if (type == short.class) {
			return new Short((short) jsArray.getInt(index));
		} else if (type == byte.class) {
			return new Byte((byte) jsArray.getInt(index));
		} else if (type == float.class) {
			return new Float((float) jsArray.getDouble(index));
		} else if (type == double.class) {
			return new Double(jsArray.getDouble(index));
		} else if (type == boolean.class || type == Boolean.class) {
			return new Boolean(jsArray.getBoolean(index));
		} else if (type == char.class) {
			return new Character(jsArray.getString(index).charAt(0));
		} else if (type == String.class) {
			return jsArray.getString(index);
		} else if (type == BigDecimal.class) {
			return new BigDecimal(jsArray.getString(index).trim());
		} else if (type == BigInteger.class) {
			return new BigDecimal(jsArray.getString(index).trim()).toBigInteger();
		} else if (type == boolean[].class) {
			return new JSONConverter().unmarshall(jsArray.get(index), new boolean[((JSONArray) jsArray.get(index)).length()]);
		} else if (type == char[].class) {
			return new JSONConverter().unmarshall(jsArray.get(index), new char[((JSONArray) jsArray.get(index)).length()]);
		} else if (type == byte[].class) {
			return new JSONConverter().unmarshall(jsArray.get(index), new byte[((JSONArray) jsArray.get(index)).length()]);
		} else if (type == short[].class) {
			return new JSONConverter().unmarshall(jsArray.get(index), new short[((JSONArray) jsArray.get(index)).length()]);
		} else if (type == int[].class) {
			return new JSONConverter().unmarshall(jsArray.get(index), new int[((JSONArray) jsArray.get(index)).length()]);
		} else if (type == long[].class) {
			return new JSONConverter().unmarshall(jsArray.get(index), new long[((JSONArray) jsArray.get(index)).length()]);
		} else if (type == float[].class) {
			return new JSONConverter().unmarshall(jsArray.get(index), new float[((JSONArray) jsArray.get(index)).length()]);
		} else if (type == double[].class) {
			return new JSONConverter().unmarshall(jsArray.get(index), new double[((JSONArray) jsArray.get(index)).length()]);
		} else {
			// return null;
			Object ret = null;
			ret = new JSONConverter().unmarshall(jsArray.get(index), createInstance(type, jsArray.get(index)));

			return ret;
		}
	}

	private static Object createInstance(Class clazz, Object value) throws Exception {
		int length = 0;
		if (value instanceof JSONArray) {
			length = ((JSONArray) value).length();
		}
		if (clazz == Map.class) {
			return new HashMap();
		} else if (clazz == Set.class) {
			return new HashSet();
		} else if (clazz == List.class) {
			return new ArrayList();
		} else if (clazz == Collection.class) {
			return new ArrayList();
		} else if (clazz == String[].class) {
			return new String[length];
		} else if (clazz == String[][].class) {
			return new String[length][length];
		}
		return clazz.newInstance();
	}

	/**
	 * @param type
	 * @param jsObject
	 * @return
	 * @throws JSONException 
	 */
	public static Object getTypedValue(Class type, JSONObject jsObject, String propertyName) throws JSONException {
		if (type == int.class) {
			return new Integer(jsObject.getInt(propertyName));
		} else if (type == long.class) {
			return new Long((long) jsObject.getDouble(propertyName));
		} else if (type == short.class) {
			return new Short((short) jsObject.getInt(propertyName));
		} else if (type == byte.class) {
			return new Byte((byte) jsObject.getInt(propertyName));
		} else if (type == float.class) {
			return new Float((float) jsObject.getDouble(propertyName));
		} else if (type == double.class) {
			return new Double(jsObject.getDouble(propertyName));
		} else if (type == String.class) {
			return jsObject.getString(propertyName);
		} else {
			return null;
			// TODO:deal with class hint
		}
	}

	/**
	 * In Spring AOP, a bean post-processor may need to know that a bean is
	 * being proxied by CGLIB and if so, get to the base class that CGLIB is
	 * proxying. The reason has to do with annotations. Apparently, when CGLIB
	 * proxies a class, clazz.isAnnotationPresent() (in Java 5) no longer
	 * returns true for annotations that *are* present on the base class.
	 * Probably a limitation with annotations & proxying.
	 * 
	 * @throws ClassNotFoundException
	 */
	private Class getOriginalClass(Class clazz) throws ClassNotFoundException {
		String className = clazz.getName();
		if (className.indexOf("$$") == -1) {
			return clazz;
		} else {
			Class originalClass = Class.forName(className.substring(0, className.indexOf("$$")));
			return originalClass;
		}
	}
}
