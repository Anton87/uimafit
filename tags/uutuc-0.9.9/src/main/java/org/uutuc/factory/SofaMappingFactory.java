/* 
 Copyright 2009 Regents of the University of Colorado.  
 All rights reserved. 

 Licensed under the Apache License, Version 2.0 (the "License"); 
 you may not use this file except in compliance with the License. 
 You may obtain a copy of the License at 

 http://www.apache.org/licenses/LICENSE-2.0 

 Unless required by applicable law or agreed to in writing, software 
 distributed under the License is distributed on an "AS IS" BASIS, 
 WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. 
 See the License for the specific language governing permissions and 
 limitations under the License.
*/
package org.uutuc.factory;

import org.apache.uima.analysis_component.AnalysisComponent;
import org.apache.uima.analysis_engine.metadata.SofaMapping;
import org.apache.uima.analysis_engine.metadata.impl.SofaMapping_impl;

/**
 * @author Philip Ogren
 */
public class SofaMappingFactory {

	public static SofaMapping createSofaMapping(String aggregateSofaName, String componentKey, String componentSofaName) {
		
		SofaMapping sofaMapping = new SofaMapping_impl();
		sofaMapping.setAggregateSofaName(aggregateSofaName);
		sofaMapping.setComponentKey(componentKey);
		sofaMapping.setComponentSofaName(componentSofaName);
		return sofaMapping;
	}
	
	public static SofaMapping createSofaMapping(String aggregateSofaName, Class<? extends AnalysisComponent> componentClass, String componentSofaName) {
		
		return createSofaMapping(aggregateSofaName, componentClass.getName(), componentSofaName);
	}

}