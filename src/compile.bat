@echo off

echo Choose Java version:

set "input="
echo 1. Java 1.4 (or Java 4)
echo 2. Java 1.8 (or Java 8)
echo 3. Both Java 1.4 and Java 1.8
echo 4. Nokia API addition for Java 1.4 (ONLY CHOOSE THIS IF YOU HAVE NOKIA PACKAGES)

echo IF YOU CHOOSE OPTION 4:
echo Please copy the "Nokia_API" folder inside this repository to VOLUME E:\, NOT C:\. And the path must be E:\Nokia_API\

set /p input="Type your input here and press Enter: "

if "%input%"=="1" (

	echo Generating class

	if not exist "classes\" (
		mkdir classes
	)

	"C:\j2sdk1.4.2_19\bin\javac.exe" -encoding UTF-8 -source 1.3 -target 1.3 -bootclasspath C:\WTK2.5.2_01\lib\midpapi20.jar;C:\WTK2.5.2_01\lib\cldcapi11.jar -d classes *.java

	echo Verifying class

	"C:\Java_ME_platform_SDK_3.4\bin\preverify.exe" -classpath classes;C:\WTK2.5.2_01\lib\midpapi20.jar;C:\WTK2.5.2_01\lib\cldcapi11.jar -d classes classes\.

	echo Packaging .jar
	"C:\j2sdk1.4.2_19\bin\jar.exe" cfm j2meDash4.jar META-INF\MANIFEST.MF assets levels -C classes .

	echo Compiled successfully

) else if "%input%"=="2" (

	echo Generating class

	if not exist "classes\" (
		mkdir classes
	)

	"C:\Program Files\Java\jdk1.8.0_202\bin\javac.exe" -encoding UTF-8 -classpath "C:\Java_ME_platform_SDK_3.4\lib\*" -d classes\ *.java

	echo Packaging .jar
	"C:\Program Files\Java\jdk1.8.0_202\bin\jar.exe" cfm j2meDash8.jar META-INF\MANIFEST.MF assets levels -C classes/ .

	echo Compiled successfully

) else if "%input%"=="3" (

	echo ### JAVA 1.4 ###
	
	echo Generating class

	if not exist "classes\" (
		mkdir classes
	)

	"C:\j2sdk1.4.2_19\bin\javac.exe" -encoding UTF-8 -source 1.3 -target 1.3 -bootclasspath C:\WTK2.5.2_01\lib\midpapi20.jar;C:\WTK2.5.2_01\lib\cldcapi11.jar -d classes *.java

	echo Verifying class

	"C:\Java_ME_platform_SDK_3.4\bin\preverify.exe" -classpath classes;C:\WTK2.5.2_01\lib\midpapi20.jar;C:\WTK2.5.2_01\lib\cldcapi11.jar -d classes classes\.

	echo Packaging .jar
	"C:\j2sdk1.4.2_19\bin\jar.exe" cfm j2meDash4.jar META-INF\MANIFEST.MF assets levels -C classes .

	echo Compiled successfully
	
	echo ### JAVA 1.8 ###
	
	echo Generating class

	if not exist "classes\" (
		mkdir classes
	)

	"C:\Program Files\Java\jdk1.8.0_202\bin\javac.exe" -encoding UTF-8 -classpath "C:\Java_ME_platform_SDK_3.4\lib\*" -d classes\ *.java

	echo Packaging .jar
	"C:\Program Files\Java\jdk1.8.0_202\bin\jar.exe" cfm j2meDash8.jar META-INF\MANIFEST.MF assets levels -C classes/ .

	echo Compiled successfully
	
	echo ### SUCCESSFULLY COMPILED BOTH VERSION ###
	
) else if "%input%"=="4" (

	echo Generating class

	if not exist "classes\" (
		mkdir classes
	)

	"C:\j2sdk1.4.2_19\bin\javac.exe" -encoding UTF-8 -source 1.3 -target 1.3 -bootclasspath C:\WTK2.5.2_01\lib\midpapi20.jar;C:\WTK2.5.2_01\lib\cldcapi11.jar;E:\Nokia_API\admin.jar;E:\Nokia_API\ant-antlr.jar;E:\Nokia_API\ant-apache-bcel.jar;E:\Nokia_API\ant-apache-bsf.jar;E:\Nokia_API\ant-apache-log4j.jar;E:\Nokia_API\ant-apache-oro.jar;E:\Nokia_API\ant-apache-regexp.jar;E:\Nokia_API\ant-apache-resolver.jar;E:\Nokia_API\ant-commons-logging.jar;E:\Nokia_API\ant-commons-net.jar;E:\Nokia_API\ant-icontract.jar;E:\Nokia_API\ant-jai.jar;E:\Nokia_API\ant-javamail.jar;E:\Nokia_API\ant-jdepend.jar;E:\Nokia_API\ant-jmf.jar;E:\Nokia_API\ant-jsch.jar;E:\Nokia_API\ant-junit.jar;E:\Nokia_API\ant-launcher.jar;E:\Nokia_API\ant-netrexx.jar;E:\Nokia_API\ant-nodeps.jar;E:\Nokia_API\ant-starteam.jar;E:\Nokia_API\ant-stylebook.jar;E:\Nokia_API\ant-swing.jar;E:\Nokia_API\ant-trax.jar;E:\Nokia_API\ant-vaj.jar;E:\Nokia_API\ant-weblogic.jar;E:\Nokia_API\ant-xalan1.jar;E:\Nokia_API\ant-xslp.jar;E:\Nokia_API\ant.jar;E:\Nokia_API\app.jar;E:\Nokia_API\beepcore.jar;E:\Nokia_API\beepcore2.jar;E:\Nokia_API\bookmarks.jar;E:\Nokia_API\bootstrap.jar;E:\Nokia_API\btenv.jar;E:\Nokia_API\BTNCFIntegrationLibrary_integration.jar;E:\Nokia_API\btsppEcho.jar;E:\Nokia_API\btsppEcho2.jar;E:\Nokia_API\catalina.jar;E:\Nokia_API\com.ibm.icu_3.4.4.1.jar;E:\Nokia_API\comm.jar;E:\Nokia_API\commons-beanutils.jar;E:\Nokia_API\commons-collections.jar;E:\Nokia_API\commons-digester.jar;E:\Nokia_API\commons-logging-api.jar;E:\Nokia_API\commons-modeler.jar;E:\Nokia_API\concurrent.jar;E:\Nokia_API\concurrent2.jar;E:\Nokia_API\connectivity.jar;E:\Nokia_API\core.jar;E:\Nokia_API\debugger.jar;E:\Nokia_API\docbrowser_1.0.0.jar;E:\Nokia_API\emulator.jar;E:\Nokia_API\FavouriteArtists.jar;E:\Nokia_API\frameanimator.jar;E:\Nokia_API\gesture.jar;E:\Nokia_API\hcodec.jar;E:\Nokia_API\helpbase-ant.jar;E:\Nokia_API\http.jar;E:\Nokia_API\http2.jar;E:\Nokia_API\inapp.jar;E:\Nokia_API\interface.jar;E:\Nokia_API\jakarta-regexp-1.3.jar;E:\Nokia_API\jasper-compiler.jar;E:\Nokia_API\jasper-runtime.jar;E:\Nokia_API\Java_Developers_Library_3.9.0.jar;E:\Nokia_API\Java_Developers_Library_3.9.1.jar;E:\Nokia_API\Java_Developers_Library_3.9.12.jar;E:\Nokia_API\jaxb-api.jar;E:\Nokia_API\jaxb-impl.jar;E:\Nokia_API\jaxb-libs.jar;E:\Nokia_API\jaxb-xjc.jar;E:\Nokia_API\JAXB_runtime.jar;E:\Nokia_API\jgraph.jar;E:\Nokia_API\jh.jar;E:\Nokia_API\jsearch.jar;E:\Nokia_API\jsp.jar;E:\Nokia_API\jsr120.jar;E:\Nokia_API\jsr135.jar;E:\Nokia_API\jsr172_rpc.jar;E:\Nokia_API\jsr172_xmlparser.jar;E:\Nokia_API\jsr177_apdu.jar;E:\Nokia_API\jsr177_crypto.jar;E:\Nokia_API\jsr179.jar;E:\Nokia_API\jsr184.jar;E:\Nokia_API\jsr205.jar;E:\Nokia_API\jsr211.jar;E:\Nokia_API\jsr226.jar;E:\Nokia_API\jsr234.jar;E:\Nokia_API\jsr75_file.jar;E:\Nokia_API\jsr75_pim.jar;E:\Nokia_API\jsr82.jar;E:\Nokia_API\jug.jar;E:\Nokia_API\jug2.jar;E:\Nokia_API\kdp.jar;E:\Nokia_API\lucene-1.4.3.jar;E:\Nokia_API\mms.jar;E:\Nokia_API\mms2.jar;E:\Nokia_API\mx4j-jmx.jar;E:\Nokia_API\namespace.jar;E:\Nokia_API\naming-common.jar;E:\Nokia_API\naming-factory.jar;E:\Nokia_API\naming-resources.jar;E:\Nokia_API\ncf11support.jar;E:\Nokia_API\ncfbase.jar;E:\Nokia_API\NCFIntegrationLibrary_Client.jar;E:\Nokia_API\NCF_Getting_Started_1.2.jar;E:\Nokia_API\NCF_Getting_Started_1.22.jar;E:\Nokia_API\NCF_Getting_Started_1.23.jar;E:\Nokia_API\nokiaui.jar;E:\Nokia_API\Nokia_SDK_1_0_Java.jar;E:\Nokia_API\Nokia_SDK_1_0_Java2.jar;E:\Nokia_API\Nokia_SDK_1_0_Java3.jar;E:\Nokia_API\org.eclipse.ant.core_3.1.100.v20060531.jar;E:\Nokia_API\org.eclipse.core.commands_3.2.0.I20060605-1400.jar;E:\Nokia_API\org.eclipse.core.contenttype_3.2.0.v20060603.jar;E:\Nokia_API\org.eclipse.core.expressions_3.2.0.v20060605-1400.jar;E:\Nokia_API\org.eclipse.core.jobs_3.2.0.v20060603.jar;E:\Nokia_API\org.eclipse.core.runtime.compatibility.auth_3.2.0.v20060601.jar;E:\Nokia_API\org.eclipse.core.runtime_3.2.0.v20060603.jar;E:\Nokia_API\org.eclipse.core.variables_3.1.100.v20060605.jar;E:\Nokia_API\org.eclipse.equinox.common_3.2.0.v20060603.jar;E:\Nokia_API\org.eclipse.equinox.preferences_3.2.0.v20060601.jar;E:\Nokia_API\org.eclipse.equinox.registry_3.2.0.v20060601.jar;E:\Nokia_API\org.eclipse.help.appserver_3.1.100.v20060510b.jar;E:\Nokia_API\org.eclipse.help.base_3.2.0.v20060601.jar;E:\Nokia_API\org.eclipse.help_3.2.0.v20060518.jar;E:\Nokia_API\org.eclipse.jface_3.2.0.I20060605-1400.jar;E:\Nokia_API\org.eclipse.osgi_3.2.0.v20060601.jar;E:\Nokia_API\org.eclipse.swt.win32.win32.x86_3.2.0.v3232m.jar;E:\Nokia_API\org.eclipse.swt_3.2.0.v3232o.jar;E:\Nokia_API\org.eclipse.ui.forms_3.2.0.v20060602.jar;E:\Nokia_API\org.eclipse.ui.workbench_3.2.0.I20060605-1400.jar;E:\Nokia_API\org.eclipse.ui_3.2.0.I20060605-1400.jar;E:\Nokia_API\org.eclipse.update.configurator_3.2.0.v20060605.jar;E:\Nokia_API\P2P.jar;E:\Nokia_API\P2P2.jar;E:\Nokia_API\parser.jar;E:\Nokia_API\pipeline.jar;E:\Nokia_API\relaxngDatatype.jar;E:\Nokia_API\rmicomm.jar;E:\Nokia_API\routeEditor.jar;E:\Nokia_API\runtime_registry_compatibility.jar;E:\Nokia_API\S40.jar;E:\Nokia_API\sdkDiagnostics.jar;E:\Nokia_API\servlet.jar;E:\Nokia_API\servlets-common.jar;E:\Nokia_API\servlets-default.jar;E:\Nokia_API\servlets-invoker.jar;E:\Nokia_API\servlets-manager.jar;E:\Nokia_API\servlets.jar;E:\Nokia_API\smsEnv.jar;E:\Nokia_API\SMSFile_integration.jar;E:\Nokia_API\SMSTest.jar;E:\Nokia_API\SMSTest2.jar;E:\Nokia_API\startup.jar;E:\Nokia_API\tomcat-coyote.jar;E:\Nokia_API\tomcat-http11.jar;E:\Nokia_API\tomcat-util.jar;E:\Nokia_API\tomcatwrapper.jar;E:\Nokia_API\util.jar;E:\Nokia_API\util2.jar;E:\Nokia_API\webapp.jar;E:\Nokia_API\wps.jar;E:\Nokia_API\wtkdev.jar;E:\Nokia_API\xrpc.jar;E:\Nokia_API\xsdlib.jar -d classes *.java

	echo Verifying class

	"C:\Java_ME_platform_SDK_3.4\bin\preverify.exe" -classpath classes;C:\WTK2.5.2_01\lib\midpapi20.jar;C:\WTK2.5.2_01\lib\cldcapi11.jar;E:\Nokia_API\admin.jar;E:\Nokia_API\ant-antlr.jar;E:\Nokia_API\ant-apache-bcel.jar;E:\Nokia_API\ant-apache-bsf.jar;E:\Nokia_API\ant-apache-log4j.jar;E:\Nokia_API\ant-apache-oro.jar;E:\Nokia_API\ant-apache-regexp.jar;E:\Nokia_API\ant-apache-resolver.jar;E:\Nokia_API\ant-commons-logging.jar;E:\Nokia_API\ant-commons-net.jar;E:\Nokia_API\ant-icontract.jar;E:\Nokia_API\ant-jai.jar;E:\Nokia_API\ant-javamail.jar;E:\Nokia_API\ant-jdepend.jar;E:\Nokia_API\ant-jmf.jar;E:\Nokia_API\ant-jsch.jar;E:\Nokia_API\ant-junit.jar;E:\Nokia_API\ant-launcher.jar;E:\Nokia_API\ant-netrexx.jar;E:\Nokia_API\ant-nodeps.jar;E:\Nokia_API\ant-starteam.jar;E:\Nokia_API\ant-stylebook.jar;E:\Nokia_API\ant-swing.jar;E:\Nokia_API\ant-trax.jar;E:\Nokia_API\ant-vaj.jar;E:\Nokia_API\ant-weblogic.jar;E:\Nokia_API\ant-xalan1.jar;E:\Nokia_API\ant-xslp.jar;E:\Nokia_API\ant.jar;E:\Nokia_API\app.jar;E:\Nokia_API\beepcore.jar;E:\Nokia_API\beepcore2.jar;E:\Nokia_API\bookmarks.jar;E:\Nokia_API\bootstrap.jar;E:\Nokia_API\btenv.jar;E:\Nokia_API\BTNCFIntegrationLibrary_integration.jar;E:\Nokia_API\btsppEcho.jar;E:\Nokia_API\btsppEcho2.jar;E:\Nokia_API\catalina.jar;E:\Nokia_API\com.ibm.icu_3.4.4.1.jar;E:\Nokia_API\comm.jar;E:\Nokia_API\commons-beanutils.jar;E:\Nokia_API\commons-collections.jar;E:\Nokia_API\commons-digester.jar;E:\Nokia_API\commons-logging-api.jar;E:\Nokia_API\commons-modeler.jar;E:\Nokia_API\concurrent.jar;E:\Nokia_API\concurrent2.jar;E:\Nokia_API\connectivity.jar;E:\Nokia_API\core.jar;E:\Nokia_API\debugger.jar;E:\Nokia_API\docbrowser_1.0.0.jar;E:\Nokia_API\emulator.jar;E:\Nokia_API\FavouriteArtists.jar;E:\Nokia_API\frameanimator.jar;E:\Nokia_API\gesture.jar;E:\Nokia_API\hcodec.jar;E:\Nokia_API\helpbase-ant.jar;E:\Nokia_API\http.jar;E:\Nokia_API\http2.jar;E:\Nokia_API\inapp.jar;E:\Nokia_API\interface.jar;E:\Nokia_API\jakarta-regexp-1.3.jar;E:\Nokia_API\jasper-compiler.jar;E:\Nokia_API\jasper-runtime.jar;E:\Nokia_API\Java_Developers_Library_3.9.0.jar;E:\Nokia_API\Java_Developers_Library_3.9.1.jar;E:\Nokia_API\Java_Developers_Library_3.9.12.jar;E:\Nokia_API\jaxb-api.jar;E:\Nokia_API\jaxb-impl.jar;E:\Nokia_API\jaxb-libs.jar;E:\Nokia_API\jaxb-xjc.jar;E:\Nokia_API\JAXB_runtime.jar;E:\Nokia_API\jgraph.jar;E:\Nokia_API\jh.jar;E:\Nokia_API\jsearch.jar;E:\Nokia_API\jsp.jar;E:\Nokia_API\jsr120.jar;E:\Nokia_API\jsr135.jar;E:\Nokia_API\jsr172_rpc.jar;E:\Nokia_API\jsr172_xmlparser.jar;E:\Nokia_API\jsr177_apdu.jar;E:\Nokia_API\jsr177_crypto.jar;E:\Nokia_API\jsr179.jar;E:\Nokia_API\jsr184.jar;E:\Nokia_API\jsr205.jar;E:\Nokia_API\jsr211.jar;E:\Nokia_API\jsr226.jar;E:\Nokia_API\jsr234.jar;E:\Nokia_API\jsr75_file.jar;E:\Nokia_API\jsr75_pim.jar;E:\Nokia_API\jsr82.jar;E:\Nokia_API\jug.jar;E:\Nokia_API\jug2.jar;E:\Nokia_API\kdp.jar;E:\Nokia_API\lucene-1.4.3.jar;E:\Nokia_API\mms.jar;E:\Nokia_API\mms2.jar;E:\Nokia_API\mx4j-jmx.jar;E:\Nokia_API\namespace.jar;E:\Nokia_API\naming-common.jar;E:\Nokia_API\naming-factory.jar;E:\Nokia_API\naming-resources.jar;E:\Nokia_API\ncf11support.jar;E:\Nokia_API\ncfbase.jar;E:\Nokia_API\NCFIntegrationLibrary_Client.jar;E:\Nokia_API\NCF_Getting_Started_1.2.jar;E:\Nokia_API\NCF_Getting_Started_1.22.jar;E:\Nokia_API\NCF_Getting_Started_1.23.jar;E:\Nokia_API\nokiaui.jar;E:\Nokia_API\Nokia_SDK_1_0_Java.jar;E:\Nokia_API\Nokia_SDK_1_0_Java2.jar;E:\Nokia_API\Nokia_SDK_1_0_Java3.jar;E:\Nokia_API\org.eclipse.ant.core_3.1.100.v20060531.jar;E:\Nokia_API\org.eclipse.core.commands_3.2.0.I20060605-1400.jar;E:\Nokia_API\org.eclipse.core.contenttype_3.2.0.v20060603.jar;E:\Nokia_API\org.eclipse.core.expressions_3.2.0.v20060605-1400.jar;E:\Nokia_API\org.eclipse.core.jobs_3.2.0.v20060603.jar;E:\Nokia_API\org.eclipse.core.runtime.compatibility.auth_3.2.0.v20060601.jar;E:\Nokia_API\org.eclipse.core.runtime_3.2.0.v20060603.jar;E:\Nokia_API\org.eclipse.core.variables_3.1.100.v20060605.jar;E:\Nokia_API\org.eclipse.equinox.common_3.2.0.v20060603.jar;E:\Nokia_API\org.eclipse.equinox.preferences_3.2.0.v20060601.jar;E:\Nokia_API\org.eclipse.equinox.registry_3.2.0.v20060601.jar;E:\Nokia_API\org.eclipse.help.appserver_3.1.100.v20060510b.jar;E:\Nokia_API\org.eclipse.help.base_3.2.0.v20060601.jar;E:\Nokia_API\org.eclipse.help_3.2.0.v20060518.jar;E:\Nokia_API\org.eclipse.jface_3.2.0.I20060605-1400.jar;E:\Nokia_API\org.eclipse.osgi_3.2.0.v20060601.jar;E:\Nokia_API\org.eclipse.swt.win32.win32.x86_3.2.0.v3232m.jar;E:\Nokia_API\org.eclipse.swt_3.2.0.v3232o.jar;E:\Nokia_API\org.eclipse.ui.forms_3.2.0.v20060602.jar;E:\Nokia_API\org.eclipse.ui.workbench_3.2.0.I20060605-1400.jar;E:\Nokia_API\org.eclipse.ui_3.2.0.I20060605-1400.jar;E:\Nokia_API\org.eclipse.update.configurator_3.2.0.v20060605.jar;E:\Nokia_API\P2P.jar;E:\Nokia_API\P2P2.jar;E:\Nokia_API\parser.jar;E:\Nokia_API\pipeline.jar;E:\Nokia_API\relaxngDatatype.jar;E:\Nokia_API\rmicomm.jar;E:\Nokia_API\routeEditor.jar;E:\Nokia_API\runtime_registry_compatibility.jar;E:\Nokia_API\S40.jar;E:\Nokia_API\sdkDiagnostics.jar;E:\Nokia_API\servlet.jar;E:\Nokia_API\servlets-common.jar;E:\Nokia_API\servlets-default.jar;E:\Nokia_API\servlets-invoker.jar;E:\Nokia_API\servlets-manager.jar;E:\Nokia_API\servlets.jar;E:\Nokia_API\smsEnv.jar;E:\Nokia_API\SMSFile_integration.jar;E:\Nokia_API\SMSTest.jar;E:\Nokia_API\SMSTest2.jar;E:\Nokia_API\startup.jar;E:\Nokia_API\tomcat-coyote.jar;E:\Nokia_API\tomcat-http11.jar;E:\Nokia_API\tomcat-util.jar;E:\Nokia_API\tomcatwrapper.jar;E:\Nokia_API\util.jar;E:\Nokia_API\util2.jar;E:\Nokia_API\webapp.jar;E:\Nokia_API\wps.jar;E:\Nokia_API\wtkdev.jar;E:\Nokia_API\xrpc.jar;E:\Nokia_API\xsdlib.jar -d classes classes\.

	echo Packaging .jar
	"C:\j2sdk1.4.2_19\bin\jar.exe" cfm j2meDash4+NOKIA.jar META-INF\MANIFEST.MF assets levels -C classes .

	echo Compiled successfully
	
)