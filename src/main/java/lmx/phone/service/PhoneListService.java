package lmx.phone.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import lmx.phone.domain.Phone;
import lmx.phone.esper.EsperPart;
import lmx.phone.util.ExtJSResponse;

import org.jboss.resteasy.logging.Logger;
import org.springframework.stereotype.Service;

/**
 * handle the data of Phone
 * @author MiXian
 *
 */
@Service
@Path("phone")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class PhoneListService {
	Logger logger = Logger.getLogger(PhoneListService.class);
	
	@Path("list")
	@GET
	public ExtJSResponse listPhone(
			@QueryParam("id") final String id,
			@QueryParam("user") final String user,
			@QueryParam("page") final String page,
			@QueryParam("start") final String start,
			@QueryParam("limit") final String limit) {
		logger.info("getting all phone records");
		
		if(EsperPart.isReady == false) {
			return ExtJSResponse.successRes();
		}
		
		Map<String, Object> resMap = new HashMap<String, Object>();
		List<Phone> reslist = null;
		List<Phone> all = EsperPart.phoneList;
		
		boolean idCon = id != null && !"".equals(id);
		boolean userCon = user != null && !"".equals(user);
		

		int most = Integer.valueOf(limit)*Integer.valueOf(page);
		int length = all.size();
		int startIndex = Integer.valueOf(start);
		int endIndex = (most < length ? most : length);
		
		// id and user are both the conditions need to considerer
		if(idCon && userCon) {
			reslist = new ArrayList<Phone>();
			for(int i= startIndex; i< endIndex; i++) {
				if(all.get(i).getId().endsWith(id) && all.get(i).getUser().equals(user)) {
					reslist.add(all.get(i));
				}
			}
		}
		// only id
		if(idCon && !userCon) {
			reslist = new ArrayList<Phone>();
			for(int i= startIndex; i< endIndex; i++) {
				if(all.get(i).getId().endsWith(id)) {
					reslist.add(all.get(i));
				}
			}
			
		}
		// only user
		if(!userCon && userCon) {
			reslist = new ArrayList<Phone>();
			for(int i= startIndex; i< endIndex; i++) {
				if(all.get(i).getUser().equals(user)) {
					reslist.add(all.get(i));
				}
			}
			
		}

		int total;
		if(reslist == null) {
			// all
			reslist = new ArrayList<Phone>();
			for(int i= startIndex; i< endIndex; i++) {
				reslist.add(all.get(i));
			}
			total = all.size();
		} else {
			total = reslist.size();
		}
		resMap.put("data", reslist);
		resMap.put("total", total);
		return ExtJSResponse.successResWithMap(resMap);
		
	}
	
}
