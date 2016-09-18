package com.jhd.services;

import java.io.IOException;

import javax.mail.MessagingException;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;


@Path("/storeservice")
public class StoreServices {
	
	  @GET
	  @Path("/getorderdetailsforstore/{storeId}")
	  @Consumes(MediaType.APPLICATION_JSON)
	  @Produces(MediaType.APPLICATION_JSON)
	  public void getOrderDetailsForStore(@PathParam("storeId")  int storeId) throws IOException, MessagingException {
		  //list<checkoutdetails> list=checkoutdetailsDataService.getAllOrderforStore(storeID)
		  //iterate over list and create a datalist
		  //retrun the order list
		
	}
	
	  
	  @GET
	  @Path("/getorderdetails/{checkoutId}")
	  @Consumes(MediaType.APPLICATION_JSON)
	  @Produces(MediaType.APPLICATION_JSON)
	  public void getOrderDetails(@PathParam("checkoutId")  int checkoutId) throws IOException, MessagingException {
		  //list<checkoutdetails> list=checkoutactionDataService.getDetailfororder(checkoutId)
		  //iterate over list and create a datalist
		  //retrun the order list
		
	}
	  
	//  mysql2://b55a6179d1d03d:fc8552a1@us-cdbr-iron-east-04.cleardb.net/heroku_eb60dc74f1ab767?reconnect=true
	  
	  @POST
	  @Path("/updateorderdetails/")
	  @Consumes(MediaType.APPLICATION_JSON)
	  public void updateOrderDetails() throws IOException, MessagingException {
		  //list<checkoutdetails> list=checkoutactionDataService.getDetailfororder(checkoutId)
		  //iterate over list and create a datalist
		  //retrun the order list
		
	}
	  

}
