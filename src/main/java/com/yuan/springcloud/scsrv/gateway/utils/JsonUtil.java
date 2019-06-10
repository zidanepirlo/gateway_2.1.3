package com.yuan.springcloud.scsrv.gateway.utils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;
import com.alibaba.fastjson.serializer.SerializeConfig;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.yuan.springcloud.scsrv.gateway.entity.JwtEntity;
import com.yuan.springcloud.scsrv.gateway.filter.BuildTokenGatewayFilterFactory;
import org.apache.commons.collections.KeyValue;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;

public class JsonUtil {

	private static final Logger logger = LoggerFactory.getLogger(JsonUtil.class);

	private static final SerializeConfig config;

	static {
		config = new SerializeConfig();
//		config.put(java.util.Date.class, new JSONLibDataFormatSerializer()); // 使用和json-lib兼容的日期输出格式
//		config.put(java.sql.Date.class, new JSONLibDataFormatSerializer()); // 使用和json-lib兼容的日期输出格式
	}

	private static final SerializerFeature[] features = {SerializerFeature.WriteMapNullValue, // 输出空置字段
			SerializerFeature.WriteNullListAsEmpty, // list字段如果为null，输出为[]，而不是null
//			SerializerFeature.WriteNullNumberAsZero, // 数值字段如果为null，输出为0，而不是null
//			SerializerFeature.WriteNullBooleanAsFalse, // Boolean字段如果为null，输出为false，而不是null
			SerializerFeature.WriteNullStringAsEmpty // 字符类型字段如果为null，输出为""，而不是null
	};


	public static String toJSONString(Object object) {
		return JSON.toJSONString(object, config, features);
	}

	public static String toJSONNoFeatures(Object object) {
		return JSON.toJSONString(object, config);
	}



	public static Object toBean(String text) {
		return JSON.parse(text);
	}

	public static <T> T toBean(String text, Class<T> clazz) {
		return JSON.parseObject(text, clazz);
	}

	// 转换为数组
	public static <T> Object[] toArray(String text) {
		return toArray(text, null);
	}

	// 转换为数组
	public static <T> Object[] toArray(String text, Class<T> clazz) {
		return JSON.parseArray(text, clazz).toArray();
	}

	// 转换为List
	public static <T> List<T> toList(String text, Class<T> clazz) {
		return JSON.parseArray(text, clazz);
	}

	public static<T> List<T> jsonToArray(final String json,Class<T> clz){

		List<Object> objects = JSON.parseArray(json);
		List<T> result = new ArrayList<>();

		if (objects == null){
			return null;
		}
		else{
			for(Object object: objects){
				result.add(JSON.parseObject(JSON.toJSONString(object), clz));
			}
		}

		return result;
	}

	/**
	 * 将javabean转化为序列化的json字符串
	 * @param keyvalue
	 * @return
	 */
	public static Object beanToJson(KeyValue keyvalue) {
		String textJson = JSON.toJSONString(keyvalue);
		Object objectJson  = JSON.parse(textJson);
		return objectJson;
	}

	/**
	 * 将string转化为序列化的json字符串
	 * @param text
	 * @return
	 */
	public static Object textToJson(String text) {
		Object objectJson  = JSON.parse(text);
		return objectJson;
	}

	/**
	 * json字符串转化为map
	 * @param s
	 * @return
	 */
	public static Map stringToCollect(String s) {
		Map m = JSONObject.parseObject(s);
		return m;
	}

	/**
	 * 将map转化为string
	 * @param m
	 * @return
	 */
	public static String collectToString(Map m) {
		String s = JSONObject.toJSONString(m);
		return s;
	}

	/**
	 * 将JSON对象转换成 URL参数拼接(a=a&b=b)
	 * 
	 * @param jsonObj
	 * @return
	 */
	public static String toUrlParam(JSONObject jsonObj) {
		List<String> params = new ArrayList<String>();

		Iterator iter = jsonObj.keySet().iterator();
		while (iter.hasNext()) {
			String key = Strings.parseString(iter.next());
			Object value = jsonObj.get(key);
			if (value instanceof Date) {
				/** 将日期格式转换成 yyyy-MM-dd **/
				value = Dates.getDateTime((Date) value, Dates.getDefaultDateFormat());
			} else {
				value = Strings.parseString(value);
			}
			params.add(key + "=" + value);
		}
		return StringUtils.join(params.toArray(), "&");
	}

	public static void main(String[] args) {

		JwtEntity jwtEntity = new JwtEntity("123456","654321","5555");
		String jsonStr = JsonUtil.toJSONString(jwtEntity);
		logger.info("jsonStr={}",jsonStr);

//		List<JwtEntity> list = JsonUtil.jsonToArray(jsonStr,JwtEntity.class);
//		for (JwtEntity je:list){
//			logger.info("JwtEntity={}",je);
//		}

//		JwtEntity jwtEntity1 = (JwtEntity) JSON.parse(jsonStr);

		JwtEntity jwtEntity1=  JSON.parseObject(jsonStr, new TypeReference<JwtEntity>() {});
		logger.info("jwtEntity1={}",jwtEntity1);


	}

}
