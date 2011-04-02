package org.uimafit.spring;

import static org.junit.Assert.assertEquals;
import static org.uimafit.factory.AnalysisEngineFactory.createPrimitive;

import org.apache.uima.analysis_component.JCasAnnotator_ImplBase;
import org.apache.uima.analysis_engine.AnalysisEngine;
import org.apache.uima.analysis_engine.AnalysisEngineProcessException;
import org.apache.uima.collection.CasConsumerDescription;
import org.apache.uima.collection.CasInitializerDescription;
import org.apache.uima.collection.CollectionReaderDescription;
import org.apache.uima.impl.CompositeResourceFactory_impl;
import org.apache.uima.impl.UIMAFramework_impl;
import org.apache.uima.jcas.JCas;
import org.apache.uima.resource.CustomResourceSpecifier;
import org.apache.uima.resource.ResourceCreationSpecifier;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigUtils;
import org.springframework.context.support.GenericApplicationContext;
import org.uimafit.spring.factory.AnalysisEngineFactory_impl;
import org.uimafit.spring.factory.CasConsumerFactory_impl;
import org.uimafit.spring.factory.CasInitializerFactory_impl;
import org.uimafit.spring.factory.CollectionReaderFactory_impl;
import org.uimafit.spring.factory.CustomResourceFactory_impl;

/**
 * Test reconfiguring the UIMA framework so that an additional Spring initialization is applied
 * after the UIMA initialization. This allows regular annotation-based Spring dependency injection.
 *
 * @author Richard Eckart de Castilho
 */
public class UimaFactoryInjectionTest {
	@Test
	public void test() throws Exception {
		// Acquire application context
		ApplicationContext ctx = getApplicationContext();

		// Configure UIMA for this context
		initUimaApplicationContext(ctx);

		// Instantiate component
		AnalysisEngine ae = createPrimitive(MyAnalysisEngine.class);

		// Test that injection works
		ae.process(ae.newJCas());
	}

	public static class MyAnalysisEngine extends JCasAnnotator_ImplBase {
		@Autowired @Qualifier("otherBean")
		private Object injectedBean;

		@Override
		public void process(JCas aJCas) throws AnalysisEngineProcessException {
			assertEquals("BEAN", injectedBean);
		}
	}

	private ApplicationContext getApplicationContext() {
		final GenericApplicationContext ctx = new GenericApplicationContext();
		AnnotationConfigUtils.registerAnnotationConfigProcessors(ctx);
		ctx.registerBeanDefinition("otherBean",
				BeanDefinitionBuilder.genericBeanDefinition(String.class)
						.addConstructorArgValue("BEAN").getBeanDefinition());

		ctx.registerBeanDefinition("analysisEngineFactory",
				BeanDefinitionBuilder.genericBeanDefinition(AnalysisEngineFactory_impl.class)
						.getBeanDefinition());
		ctx.registerBeanDefinition("casConsumerFactory",
				BeanDefinitionBuilder.genericBeanDefinition(CasConsumerFactory_impl.class)
						.getBeanDefinition());
		ctx.registerBeanDefinition("casInitializerFactory",
				BeanDefinitionBuilder.genericBeanDefinition(CasInitializerFactory_impl.class)
						.getBeanDefinition());
		ctx.registerBeanDefinition("collectionReaderFactory",
				BeanDefinitionBuilder.genericBeanDefinition(CollectionReaderFactory_impl.class)
						.getBeanDefinition());
		ctx.registerBeanDefinition("customResourceFactory",
				BeanDefinitionBuilder.genericBeanDefinition(CustomResourceFactory_impl.class)
						.getBeanDefinition());
		ctx.refresh();
		return ctx;
	}

	private static void initUimaApplicationContext(final ApplicationContext aApplicationContext)
	{
		new UIMAFramework_impl() {
			{
				CompositeResourceFactory_impl factory = (CompositeResourceFactory_impl) getResourceFactory();
				factory.registerFactory(CasConsumerDescription.class,
						aApplicationContext.getBean(CasConsumerFactory_impl.class));
				factory.registerFactory(CasInitializerDescription.class,
						aApplicationContext.getBean(CasInitializerFactory_impl.class));
				factory.registerFactory(CollectionReaderDescription.class,
						aApplicationContext.getBean(CollectionReaderFactory_impl.class));
				factory.registerFactory(ResourceCreationSpecifier.class,
						aApplicationContext.getBean(AnalysisEngineFactory_impl.class));
				factory.registerFactory(CustomResourceSpecifier.class,
						aApplicationContext.getBean(CustomResourceFactory_impl.class));
			}
		};
	}
}