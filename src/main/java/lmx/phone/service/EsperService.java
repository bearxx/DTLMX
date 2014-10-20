package lmx.phone.service;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import lmx.phone.esper.EsperPart;
import lmx.phone.util.ExtJSResponse;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

@Service
@Path("esper")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class EsperService {
	
	private final static Logger logger = Logger.getLogger(EsperService.class);

	@Path("startPhone")
	@GET
	public ExtJSResponse startPhoneService() throws Exception {
		try {
			logger.info("esper phone service start to init");
			EsperPart.init();
			logger.info("esper phone service has inited");
			logger.info("esper phone service start to wait for events");
			Thread startPhoneEsper = new Thread() {
				public void run() {
					try {
						EsperPart.runEsper();
					} catch (Exception e) {
						logger.error(e);
					}					
				}
			};				
			startPhoneEsper.start();
			while(EsperPart.isReady == false) {
				Thread.sleep(100);
			}
		}catch(Exception e) {
			logger.error(e);
			throw(e);
		}
		return ExtJSResponse.successRes();
		
	}
	
	@Path("phone")
	@GET
	public ExtJSResponse phoneService(final @QueryParam("id") String id,
			final @QueryParam("user") String user,
			final @QueryParam("payment") String payment) throws Exception{
		logger.info("[new event]");
		EsperPart.addRealRecordToFile(id, user, Integer.valueOf(payment));
		return ExtJSResponse.successResWithData(EsperPart.phoneList);
	}
	
	@Path("stopPhone")
	@GET
	public ExtJSResponse stopPhoneService() throws Exception{
		try {
			logger.info("esper service begin to stopping");
			EsperPart.stop();
			logger.info("esper service has stopped");
		}catch(Exception e) {
			logger.error(e);
			throw(e);
		}
		return ExtJSResponse.successResWithData(true);
	}
	
	@Path("result")
	@GET
	public ExtJSResponse getResult() throws Exception{
		while(EsperPart.isNewRes == false){
			Thread.sleep(100);
		};
		EsperPart.isNewRes = false;
		return ExtJSResponse.successResWithData(EsperPart.result);
	}
	
	@Path("status")
	@GET
	public ExtJSResponse getEsperStatus() {
		return ExtJSResponse.successResWithKeyValue("isRunning", EsperPart.isReady);
	}

}
