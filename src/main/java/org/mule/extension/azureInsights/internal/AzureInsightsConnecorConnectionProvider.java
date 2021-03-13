package org.mule.extension.azureInsights.internal;

import org.mule.runtime.api.connection.ConnectionException;
import org.mule.runtime.extension.api.annotation.param.Parameter;
import org.mule.runtime.extension.api.annotation.param.Optional;
import org.mule.runtime.api.connection.ConnectionValidationResult;
import org.mule.runtime.api.connection.PoolingConnectionProvider;
import org.mule.runtime.api.connection.ConnectionProvider;
import org.mule.runtime.api.connection.CachedConnectionProvider;
import org.mule.runtime.extension.api.annotation.param.display.DisplayName;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.microsoft.applicationinsights.TelemetryClient;
import com.microsoft.applicationinsights.telemetry.SeverityLevel;


/**
 * This class (as it's name implies) provides connection instances and the funcionality to disconnect and validate those
 * connections.
 * <p>
 * All connection related parameters (values required in order to create a connection) must be
 * declared in the connection providers.
 * <p>
 * This particular example is a {@link PoolingConnectionProvider} which declares that connections resolved by this provider
 * will be pooled and reused. There are other implementations like {@link CachedConnectionProvider} which lazily creates and
 * caches connections or simply {@link ConnectionProvider} if you want a new connection each time something requires one.
 */
public class AzureInsightsConnecorConnectionProvider implements PoolingConnectionProvider<AzureInsightsConnecorConnection> {

  private final Logger LOGGER = LoggerFactory.getLogger(AzureInsightsConnecorConnectionProvider.class);

 /**
  * A parameter that is always required to be configured.
  */
  @Parameter
  private String instrumentionId;


  @Override
  public AzureInsightsConnecorConnection connect() throws ConnectionException {
    return new AzureInsightsConnecorConnection(instrumentionId);
  }

  @Override
  public void disconnect(AzureInsightsConnecorConnection connection) {
    try {
      connection.invalidate();
    } catch (Exception e) {
      LOGGER.error("Error while disconnecting [" + connection.getId() + "]: " + e.getMessage(), e);
    }
  }

  @Override
  public ConnectionValidationResult validate(AzureInsightsConnecorConnection connection) {
	  try 
	  {
		  TelemetryClient telemetryClient = new TelemetryClient();
			telemetryClient.getContext().setInstrumentationKey(connection.getId().toString());
			telemetryClient.trackTrace("MuleSoft connection test", SeverityLevel.Information);
	  } catch(Exception ex)
	  {
		  return ConnectionValidationResult.failure(ex.toString(), ex);
	  }
    return ConnectionValidationResult.success();
  }
}
