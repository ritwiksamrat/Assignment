import java.util.Iterator;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.XML;

public class JsonCreation {

	
	public static void main(String args[]) throws JSONException
	{
		
		
		String jsonData = "{\"startDate\":\"2020-06-27\",\"endDate\":\"2020-06-27\",\"taxInclusive\":true,\"rooms\":[{\"rateplans\":[{\"rate\":{\"rackRate\":2208},\"rateplanId\":\"executive-s-cp\"},{\"rate\":{\"rackRate\":2508},\"rateplanId\":\"executive-d-cp\"}],\"roomId\":\"executive\"},{\"rateplans\":[{\"rate\":{\"rackRate\":1008},\"rateplanId\":\"super-deluxe-s-ep\"},{\"rate\":{\"rackRate\":2008},\"rateplanId\":\"super-deluxe-d-ep\"}],\"roomId\":\"super-deluxe\"}]}";
		
		JSONObject jsonObj = new JSONObject(jsonData.trim());
		JSONArray keys = jsonObj.names ();
		JSONObject resRequest= new JSONObject();
		resRequest.put("Request_Type", "UpdateRoomRates");
		JSONArray rateType= new JSONArray();

		for (int i = 0; i < keys.length (); ++i) {
			String key = keys.getString (i); 
		   if(key.equals("rooms")) {
			  JSONArray rooms = jsonObj.getJSONArray(key);
			  
			  for (int j = 0; j < rooms.length(); j++) {
				  
				  JSONObject room = rooms.getJSONObject(j);
				  JSONArray ratePlans = room.getJSONArray("rateplans");
				  
				  for(int k=0; k < ratePlans.length(); k++) {
					  
					  JSONObject setRooms = new JSONObject();
					  setRooms.put("RoomTypeID", room.get("roomId"));
					  JSONObject ratePlan = ratePlans.getJSONObject(k);
					  setRooms.put("RateTypeID", ratePlan.get("rateplanId"));
					  setRooms.put("FromDate", jsonObj.get("startDate"));
					  setRooms.put("ToDate", jsonObj.get("endDate"));
					  setRooms.put("Taxinclusive", jsonObj.get("taxInclusive"));
					  JSONObject rate = ratePlan.getJSONObject("rate");
					  JSONObject base = new JSONObject();
					  setRooms.put("RoomRate", base.put("Base", rate.get("rackRate")));
					  rateType.put(setRooms);
				  }
			}
			  
		}
		   
	}
		
		resRequest.put("RateType", rateType);
		JSONObject finalObject = new JSONObject();
		finalObject.put("RES_Request", resRequest);
		String xmlString=XML.toString(finalObject) ;
		System.out.println(xmlString);
	
	}
	
}
