package edu.girish.asu.CrimeData;

import java.util.AbstractMap;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.converters.Converter;
import com.thoughtworks.xstream.converters.MarshallingContext;
import com.thoughtworks.xstream.converters.UnmarshallingContext;
import com.thoughtworks.xstream.io.HierarchicalStreamReader;
import com.thoughtworks.xstream.io.HierarchicalStreamWriter;

class MapToXmlConverter {
	protected static String ConvertMapToXMLString(Map<String, Integer> inputMap) throws Exception {
		XStream xstream = new XStream();
		xstream.alias("crime", Map.class);
		xstream.registerConverter(new MapEntryConverter());
		String xml = xstream.toXML(inputMap);
		return xml;
	}

	public static class MapEntryConverter implements Converter {
		public boolean canConvert(Class clazz) {
			return AbstractMap.class.isAssignableFrom(clazz);
		}

		public void marshal(Object value, HierarchicalStreamWriter writer, MarshallingContext context) {
			AbstractMap map = (AbstractMap) value;
			for (Object obj : map.entrySet()) {
				Entry entry = (Entry) obj;
				writer.startNode(entry.getKey().toString());
				writer.setValue(entry.getValue().toString());
				writer.endNode();
			}
		}

		public Object unmarshal(HierarchicalStreamReader reader, UnmarshallingContext context) {
			Map<String, String> map = new HashMap<String, String>();
			while (reader.hasMoreChildren()) {
				reader.moveDown();
				map.put(reader.getNodeName(), reader.getValue());
				reader.moveUp();
			}
			return map;
		}
	}
}