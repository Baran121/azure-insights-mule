package org.mule.extension.azureInsights.internal;

import static org.mule.runtime.extension.api.annotation.param.MediaType.ANY;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.mule.runtime.extension.api.annotation.param.MediaType;

import com.microsoft.applicationinsights.TelemetryClient;
import com.microsoft.applicationinsights.telemetry.SeverityLevel;

import org.mule.runtime.api.connection.ConnectionValidationResult;
import org.mule.runtime.extension.api.annotation.param.Config;
import org.mule.runtime.extension.api.annotation.param.Connection;

/**
 * This class is a container for operations, every public method in this class will be taken as an extension operation.
 */
public class AzureInsightsConnecorOperations {

  /**
   * Example of an operation that uses the configuration and a connection instance to perform some action.
   */
  @MediaType(value = ANY, strict = false)
  public String warn(@Config AzureInsightsConnecorConfiguration configuration, @Connection AzureInsightsConnecorConnection connection, String message){
	  try {
	  TelemetryClient telemetryClient = new TelemetryClient();
		
	  telemetryClient.getContext().setInstrumentationKey(connection.getId().toString());
	  telemetryClient.trackTrace(message, SeverityLevel.Warning);
  } catch(Exception ex)
  {
	  System.out.println(ex.toString());
  }
		
	 
	  return "] with Connection id [" + connection.getId() + "]";
  }

  @MediaType(value = ANY, strict = false)
  public String error(@Config AzureInsightsConnecorConfiguration configuration, @Connection AzureInsightsConnecorConnection connection, String message){
try {
		TelemetryClient telemetryClient = new TelemetryClient();
		
		telemetryClient.getContext().setInstrumentationKey(connection.getId().toString());
		telemetryClient.trackTrace(message, SeverityLevel.Critical);} catch(Exception ex)
		  {
			  System.out.println(ex.toString());
		  }
		
	  return "] with Connection id [" + connection.getId() + "]";
  }

  @MediaType(value = ANY, strict = false)
  public String info(@Config AzureInsightsConnecorConfiguration configuration, @Connection AzureInsightsConnecorConnection connection, String message){
		try
		{
	  TelemetryClient telemetryClient = new TelemetryClient();
		telemetryClient.getContext().setInstrumentationKey(connection.getId().toString());
		telemetryClient.trackTrace(message, SeverityLevel.Information);
		
		} catch(Exception ex)
		  {
			  System.out.println(ex.toString());
		  }
	  return "] with Connection id [" + connection.getId() + "]";
  }

  @MediaType(value = ANY, strict = false)
  public String event(@Config AzureInsightsConnecorConfiguration configuration, @Connection AzureInsightsConnecorConnection connection, String message){
	  	try {
		TelemetryClient telemetryClient = new TelemetryClient();
		
		telemetryClient.getContext().setInstrumentationKey(connection.getId().toString());
		telemetryClient.trackEvent(message);} catch(Exception ex)
		  {
			  System.out.println(ex.toString());
		  }
		   
	  return "] with Connection id [" + connection.getId() + "]";
  }

}
