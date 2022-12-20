package com.zheta.quarkus.ocr.exception;

import javax.ws.rs.core.Response;

import org.jboss.resteasy.reactive.RestResponse;
import org.jboss.resteasy.reactive.server.ServerExceptionMapper;

import com.zheta.quarkus.ocr.model.Error;

public class ExceptionMappers {
	
	@ServerExceptionMapper
	public RestResponse<Error> map(Exception e) {
		return RestResponse.status(Response.Status.INTERNAL_SERVER_ERROR, new Error(e));
	}
	
}
