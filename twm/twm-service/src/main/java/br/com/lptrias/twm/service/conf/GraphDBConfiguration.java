package br.com.lptrias.twm.service.conf;

import java.io.IOException;
import java.io.InputStream;
import java.util.Map.Entry;
import java.util.Properties;

import org.apache.commons.configuration.BaseConfiguration;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;

import com.thinkaurelius.titan.core.TitanFactory;
import com.tinkerpop.blueprints.Graph;

@Configuration
public class GraphDBConfiguration {
	
	private static final Logger LOGGER = LogManager.getLogger();
	
	@Bean(name="graph", destroyMethod="shutdown")
	public Graph configureGraph(){
		
		Resource res = new ClassPathResource("titan-cassandra-es.properties");
		
		Properties prop = new Properties();
		
		try (InputStream is = res.getInputStream()){
			prop.load(is);
		} catch (IOException e) {
			LOGGER.error("Can't load " + res);
		}
		
		BaseConfiguration conf = new BaseConfiguration();
		
		for (Entry<Object, Object> e : prop.entrySet()) {
			conf.setProperty((String)e.getKey(), e.getValue());
		}
		
		return TitanFactory.open(conf);
	}
}
