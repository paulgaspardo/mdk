package gov.nasa.jpl.mbee.mdk.mms.actions;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;

import com.nomagic.magicdraw.core.Application;
import com.nomagic.magicdraw.core.Project;
import com.nomagic.magicdraw.core.project.ProjectDescriptor;
import com.nomagic.magicdraw.core.project.ProjectDescriptorsFactory;
import com.nomagic.magicdraw.core.project.ProjectsManager;
import com.nomagic.magicdraw.tests.MagicDrawTestRunner;
import com.nomagic.magicdraw.uml.BaseElement;
import com.nomagic.uml2.ext.magicdraw.classes.mdkernel.Element;

import gov.nasa.jpl.mbee.mdk.api.MagicDrawHelper;
import gov.nasa.jpl.mbee.mdk.options.MDKOptionsGroup;
import static org.junit.Assert.assertTrue;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * Create a PDF from MDK Document Model
 * 
 * 
 * @author miyako wilson
 * @JIRA Cameo MDK MDK-64
 *
 */
@RunWith(MagicDrawTestRunner.class)
public class GeneratePDFFromMDModelTest 
{
    /**
     * Project on which test is performed.
     */
    private static Project project;
    private static File docbookXslFo;
    //private static File outputPdfFile;
    /**
     * Constructs this test.
 	 *
     */
    public GeneratePDFFromMDModelTest(){}
    
   

    @BeforeClass
    public static void setupProject() throws Exception  {

    	String userdir = System.getProperty("user.dir");
        File docGenProjectFile = new File(userdir + File.separator + "samples" + File.separator + "MDK" + File.separator + "DocGen.mdzip");
        //File testProjectFile = File.createTempFile("prj", ".mdzip");
        //IOUtils.copy(classLoader.getResourceAsStream("DocGen.mdzip"), new FileOutputStream(testProjectFile));
        File testProjectFile = new File(userdir, "DocGenCopied1.mdzip");
        FileUtils.copyFile(docGenProjectFile, testProjectFile);
        
        MDKOptionsGroup.getMDKOptions().setDefaultValues();
        MDKOptionsGroup.getMDKOptions().setLogJson(true);
        MagicDrawHelper.openProject(testProjectFile);
        project = Application.getInstance().getProject();
                        
        //default stylesheet from "Document Modeling Plugin"
    	/*docbookXslFo = new File(userdir + File.separator + "plugins" + File.separator + "com.nomagic.magicdraw.modelbasedreport" + File.separator + "xsl" + File.separator + "docbook-xsl-1.78.1" + File.separator + "fo" + File.separator + "docbook.xsl");
		if ( !docbookXslFo.exists())
			throw new Exception("\"Document Modeling Plugin\" is not installed.");
		*/
        
        //ClassLoader classLoader = GeneratePDFFromMDModelTest.class.getClassLoader();
        docbookXslFo = new File(userdir + File.separator + "plugins" + File.separator + "gov.nasa.jpl.cae.magicdraw.mdk" + File.separator + "docbook-xsl-1.79.1" + File.separator + "fo" + File.separator  + "mdk-default.xsl");
        //docbookXslFo = new File(userdir + File.separator + "plugins" + File.separator + "gov.nasa.jpl.cae.magicdraw.mdk" + File.separator + "docbook-xsl-1.79.1" + File.separator + "fo" + File.separator  + "mdk-default-docbook.xsl");
        //docbookXslFo = new File(userdir + File.separator + "plugins" + File.separator + "gov.nasa.jpl.cae.magicdraw.mdk" + File.separator + "docbook-xsl-1.79.1" + File.separator + "fo" + File.separator  + "docbook.xsl");
        //docbookXslFo = new File(userdir + File.separator + "plugins" + File.separator + "gov.nasa.jpl.cae.magicdraw.mdk" + File.separator + "docbook-xsl-1.79.1" + File.separator + "fo" + File.separator  + "mms-gt.xsl");
        //docbookXslFo = File.createTempFile("stylesheet",  ".xsl");
		//IOUtils.copy(classLoader.getResourceAsStream("docbook-xsl-1.78.1" + File.separator + "fo" + File.separator  + "docbook.xsl"), new FileOutputStream(docbookXslFo));
        if ( !docbookXslFo.exists())
        	throw new Exception("\"docbook.xsl\" is not found in mdk plugin.");
    
        
        
		//outputPdfFile = new File(userdir + File.separator + "Test-DocGenUsersGuide-MDKModeltoPDF.pdf");
		
    }
    @Test
    public void init() {
        assertTrue(project != null);
        assertTrue(docbookXslFo.exists());
    }
    @Test 
    public void testMDKModeltoPDFDocGenUsersGuide() {
    	
    	File outputPdfFile = new File(System.getProperty("user.dir") + File.separator + "testPDF.pdf");
    	try {
    		//_18_5_3_8bf0285_1518738536487_780455_42886 DocGen UsersGuide Document
    		BaseElement documentElement = project.getElementByID("_18_5_3_8bf0285_1518738536487_780455_42886");
    		GeneratePDF xx = new GeneratePDF((Element)documentElement);
    		File autoGeneratedDocbookfile = new File(outputPdfFile + ".xml");
    		xx.generate(autoGeneratedDocbookfile, docbookXslFo, outputPdfFile);
    	} catch (Exception e) {
    		e.printStackTrace();
    	}
    	assertTrue(outputPdfFile.exists());
    }
    //using DocBook XML file created by the previous test to create pdf file.
    @Test 
    public void testXMLtoPDF() {
    	File docgenFile =new File(System.getProperty("user.dir") + File.separator + "testPDF.pdf.xml");
    	File outputPdfFile = new File(System.getProperty("user.dir") + File.separator + "testPDFFromXML.pdf");
    	try {
    		//_18_5_3_8bf0285_1518738536487_780455_42886 DocGen UsersGuide Document
    		BaseElement documentElement = project.getElementByID("_18_5_3_8bf0285_1518738536487_780455_42886");
    		GeneratePDFFromDocBookDocument gp = new GeneratePDFFromDocBookDocument((Element)documentElement);
    		gp.generate(docgenFile, docbookXslFo, outputPdfFile);
    	} catch (Exception e) {
    		e.printStackTrace();
    	}
    	assertTrue(outputPdfFile.exists());
    }
    /*
    @Test 
    public void testCompareFile() {
    	
    	File outputPdfFile = new File(System.getProperty("user.dir") + File.separator + "testMDKModeltoPDFDocGenUsersGuide_fromXML.pdf");
    	assertTrue(outputPdfFile.exists());
    }
	*/
    
    @AfterClass
    public static void closeProject() throws IOException {
        MagicDrawHelper.closeProject();
    }

}