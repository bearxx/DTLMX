package lmx.phone.service;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.QueryParam;

import lmx.phone.esper.EsperPart;
import lmx.phone.util.ExtJSResponse;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

@Service
@Path("esper")
public class EsperService {
	
	private final static Logger logger = Logger.getLogger(EsperService.class);

	@Path("startPhone")
	@GET
	public void startPhoneService() throws Exception {
		System.out.println("start phone");
		try {
			System.out.println("zyz2");
			logger.info("init");
			System.out.println("zyz3");
			EsperPart.init();
			logger.info("inited");
			logger.info("phone esper begin to run");
			EsperPart.runEsper();
			System.out.println("zyz4");
		}catch(Exception e) {
			logger.error(e);
			throw(e);
		}
		
	}
	
	@Path("phone")
	@GET
	public ExtJSResponse phoneService(final @QueryParam("id") String id,
			final @QueryParam("user") String user,
			final @QueryParam("payment") String payment) throws Exception{
		EsperPart.addRealRecord(id, user, Integer.valueOf(payment));
		return ExtJSResponse.successResWithData(EsperPart.phoneList);
	}
	
	@Path("stopPhone")
	@GET
	public ExtJSResponse stopPhoneService() throws Exception{
		try {
			logger.info("esper service stopping");
			EsperPart.stop();
			logger.info("esper service stopped");
		}catch(Exception e) {
			logger.error(e);
			throw(e);
		}
		return ExtJSResponse.successResWithData(true);
	}
	
	@Path("zyz")
	@GET
	public void testZYZ() {
		System.out.println("zyz is a man");
	}

}
