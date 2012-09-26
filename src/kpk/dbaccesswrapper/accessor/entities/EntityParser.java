package kpk.dbaccesswrapper.accessor.entities;

import java.util.ArrayList;

import kpk.dbaccesswrapper.accessor.utils.EntityFieldNameConverter;

import android.content.ContentValues;


public class EntityParser {
	
	private static String sTAG = EntityParser.class.getSimpleName();
	public static <T extends AbstractObjectModel> ContentValues writeEntityIntoContentValues(T entity) {
		
		ArrayList<String> objectFields = entity.getFields();
		ArrayList<String> objectFieldTypes = entity.getFieldTypes();
		ArrayList<Object> objectFieldValues = entity.getFieldValues();
		final ContentValues cv = new ContentValues();
		for(int i = 0; i < objectFields.size(); i++) {
			
			if(objectFieldTypes.get(i).equals("int")) {
				cv.put(EntityFieldNameConverter.fromCamelCase(objectFields.get(i)), Integer.valueOf(objectFieldValues.get(i).toString()));
			}
			if(objectFieldTypes.get(i).equals("char")) {
				cv.put(EntityFieldNameConverter.fromCamelCase(objectFields.get(i)), objectFieldValues.get(i).toString());
			}
			if(objectFieldTypes.get(i).equals("byte")) {
				cv.put(EntityFieldNameConverter.fromCamelCase(objectFields.get(i)), Byte.valueOf(objectFieldValues.get(i).toString()));
			}
			if(objectFieldTypes.get(i).equals("String")) {
				cv.put(EntityFieldNameConverter.fromCamelCase(objectFields.get(i)), objectFieldValues.get(i).toString());
			}
			if(objectFieldTypes.get(i).equals("short")) {
				cv.put(EntityFieldNameConverter.fromCamelCase(objectFields.get(i)), Short.valueOf(objectFieldValues.get(i).toString()));
			}
			if(objectFieldTypes.get(i).equals("long")) {
				cv.put(EntityFieldNameConverter.fromCamelCase(objectFields.get(i)), Long.valueOf(objectFieldValues.get(i).toString()));
			}
			if(objectFieldTypes.get(i).equals("boolean")) {
				cv.put(EntityFieldNameConverter.fromCamelCase(objectFields.get(i)), Boolean.valueOf(objectFieldValues.get(i).toString()));
			}
			if(objectFieldTypes.get(i).equals("float")) {
				cv.put(EntityFieldNameConverter.fromCamelCase(objectFields.get(i)), Float.valueOf(objectFieldValues.get(i).toString()));
			}
			if(objectFieldTypes.get(i).equals("double")) {
				cv.put(EntityFieldNameConverter.fromCamelCase(objectFields.get(i)), Double.valueOf(objectFieldValues.get(i).toString()));
			}
		}
		
		return cv;
		
	}
}
