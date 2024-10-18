package org.emoflon.ibex.tgg.run.tggproject.config;

import java.io.File;
import java.io.IOException;

import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.emoflon.ibex.tgg.operational.csp.constraints.factories.tggproject.UserDefinedRuntimeTGGAttrConstraintFactory;
import org.emoflon.ibex.tgg.operational.defaults.IbexOptions;
import org.emoflon.ibex.tgg.operational.strategies.modules.IbexExecutable;
import org.emoflon.ibex.tgg.operational.strategies.opt.BWD_OPT;
import org.emoflon.ibex.tgg.operational.strategies.opt.FWD_OPT;
import org.emoflon.ibex.tgg.runtime.hipe.HiPETGGEngine;
import org.emoflon.ibex.tgg.compiler.defaults.IRegistrationHelper;

import TGGProject.TGGProjectPackage;
import TGGProject.impl.TGGProjectPackageImpl;
import MetamodelA.impl.MetamodelAPackageImpl;
import MetamodelB.impl.MetamodelBPackageImpl;

public class HiPERegistrationHelper implements IRegistrationHelper {
	
	/** Create default options **/
	public final void setWorkspaceRootDirectory(ResourceSet resourceSet) throws IOException {
		final String root = "../";
		URI key = URI.createPlatformResourceURI("/", true);
		URI value = URI.createFileURI(new File(root).getCanonicalPath() + File.separatorChar);
		resourceSet.getURIConverter().getURIMap().put(key, value);
	}

	/** Load and register source and target metamodels */
	public void registerMetamodels(ResourceSet rs, IbexExecutable executable) throws IOException {
		
		// Set correct workspace root
		setWorkspaceRootDirectory(rs);
		
		// Load and register source and target metamodels
		EPackage metamodelaPack = null;
		EPackage metamodelbPack = null;
		EPackage tggprojectPack = null;
		
		if(executable instanceof FWD_OPT) {
			Resource res = executable.getResourceHandler().loadResource("platform:/resource/MetamodelB/model/MetamodelB.ecore");
			metamodelbPack = (EPackage) res.getContents().get(0);
			rs.getResources().remove(res);
			
			res = executable.getResourceHandler().loadResource("platform:/resource/TGGProject/model/TGGProject.ecore");
			tggprojectPack = (EPackage) res.getContents().get(0);
			rs.getResources().remove(res);
		}
				
		if(executable instanceof BWD_OPT) {
			Resource res = executable.getResourceHandler().loadResource("platform:/resource/MetamodelA/model/MetamodelA.ecore");
			metamodelaPack = (EPackage) res.getContents().get(0);
			rs.getResources().remove(res);
			
			res = executable.getResourceHandler().loadResource("platform:/resource/TGGProject/model/TGGProject.ecore");
			tggprojectPack = (EPackage) res.getContents().get(0);
			rs.getResources().remove(res);
		}

		if(metamodelaPack == null)
			metamodelaPack = MetamodelAPackageImpl.init();
				
		if(metamodelbPack == null)
			metamodelbPack = MetamodelBPackageImpl.init();
		
		if(tggprojectPack == null) {
			tggprojectPack = TGGProjectPackageImpl.init();
			rs.getPackageRegistry().put("platform:/resource/TGGProject/model/TGGProject.ecore", TGGProjectPackage.eINSTANCE);
			rs.getPackageRegistry().put("platform:/plugin/TGGProject/model/TGGProject.ecore", TGGProjectPackage.eINSTANCE);
		}
			
		rs.getPackageRegistry().put("platform:/resource/MetamodelA/model/MetamodelA.ecore", metamodelaPack);
	    rs.getPackageRegistry().put("platform:/plugin/MetamodelA/model/MetamodelA.ecore", metamodelaPack);	
			
		rs.getPackageRegistry().put("platform:/resource/MetamodelB/model/MetamodelB.ecore", metamodelbPack);
		rs.getPackageRegistry().put("platform:/plugin/MetamodelB/model/MetamodelB.ecore", metamodelbPack);
	}

	/** Create default options **/
	public IbexOptions createIbexOptions() {
		IbexOptions options = new IbexOptions();
		options.blackInterpreter(new HiPETGGEngine());
		options.project.name("TGGProject");
		options.project.path("TGGProject");
		options.debug.ibexDebug(false);
		options.csp.userDefinedConstraints(new UserDefinedRuntimeTGGAttrConstraintFactory());
		options.registrationHelper(this);
		return options;
	}
}
