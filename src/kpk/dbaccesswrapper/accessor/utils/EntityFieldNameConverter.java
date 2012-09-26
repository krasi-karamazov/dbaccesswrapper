package kpk.dbaccesswrapper.accessor.utils;


@SuppressWarnings("unused")
public class EntityFieldNameConverter {
	
	private static String sTAG = EntityFieldNameConverter.class.getSimpleName();
	
	public static String fromCamelCase(String fieldName) {
		
		final StringBuilder builder = new StringBuilder();	
		for(int i = 0; i < fieldName.length(); i++) {
			
			if(Character.isUpperCase(fieldName.charAt(i))) {
				
				builder.append("_" + fieldName.charAt(i));
			}else{
				
				builder.append(Character.toUpperCase(fieldName.charAt(i)));
			}
			
		}
		
		return builder.toString();
	}
	
	public static String fromUpperCaseUnderscore(String fieldName) {
		
		final StringBuilder builder = new StringBuilder();
		for(int i = 0; i < fieldName.length(); i++) {
			
			if(fieldName.charAt(i) == '_') {
				
				builder.append(fieldName.charAt(i + 1));
				i++;
				
			}else{
				
				builder.append(Character.toLowerCase(fieldName.charAt(i)));
			}
			
		}
		
		return builder.toString();
	}
	
	public static String getSQLiteTypeString(String javaType) {
		
		if(javaType.equals("int")) {
			return "int";
		}
		if(javaType.equals("char")) {
			return "text";
		}
		if(javaType.equals("byte")) {
			return "int";
		}
		if(javaType.equals("class java.lang.String")) {
			return "text";
		}
		if(javaType.equals("java.lang.String")) {
			return "text";
		}
		if(javaType.equals("short")) {
			return "int";
		}
		if(javaType.equals("long")) {
			return "int";
		}
		if(javaType.equals("boolean")) {
			return "bool";
		}
		if(javaType.equals("float")) {
			return "real";
		}
		if(javaType.equals("double")) {
			return "real";
		}
		if(javaType.equals("[B")) {
			return "blob";
		}
		
		return "";
	}
}
