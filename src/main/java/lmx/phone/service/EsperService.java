package lmx.phone.service;

import javax.ws.rs.GET;
import javax.ws.rs.Path;

import lmx.phone.util.ExtJSResponse;

import org.springframework.stereotype.Service;

@Service
@Path("esper")
public class EsperService {

	@Path("phone")
	@GET
	public ExtJSResponse phoneService() {
		System.out.println("rest works");
		return null;
	}

}
