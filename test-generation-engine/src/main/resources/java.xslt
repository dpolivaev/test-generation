<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
xmlns:java="http://www.oracle.com/XSL/Transform/java/org.dpolivaev.tsgen.scriptwriter.TransformationHelper"
>
<xsl:output method="text" encoding="utf-8"/>
	<xsl:template name="eol">
		<xsl:text>
</xsl:text>
	</xsl:template>

	<xsl:template name="eol1">
		<xsl:text>
	</xsl:text>
	</xsl:template>

	<xsl:template name="eol2">
		<xsl:text>
		</xsl:text>
	</xsl:template>

	<xsl:template name="eol3">
		<xsl:text>
			</xsl:text>
	</xsl:template>
	
	<xsl:template name="Coverage">
		<xsl:if test="Goal">
			<xsl:call-template name="eol1"/>
			<xsl:text>@Coverage(</xsl:text>
			<xsl:if test="Goal/Item[@firstTime='true']">
				<xsl:call-template name="eol2"/>
				<xsl:text>first = {</xsl:text>
				<xsl:apply-templates select="Goal/Item[@firstTime='true']"/>
				<xsl:call-template name="eol2"/>
				<xsl:text>}</xsl:text>
			</xsl:if>
			<xsl:if test="Goal/Item[@firstTime='true'] and Goal/Item[@firstTime='false']">
				<xsl:text>,</xsl:text>
			</xsl:if>
			<xsl:if test="Goal/Item[@firstTime='false']">
				<xsl:call-template name="eol2"/>
				<xsl:text>again = {</xsl:text>
				<xsl:apply-templates select="Goal/Item[@firstTime='false']"/>
				<xsl:call-template name="eol2"/>
				<xsl:text>}</xsl:text>
			</xsl:if>
			<xsl:call-template name="eol1"/>
			<xsl:text>)</xsl:text>
		</xsl:if>
	</xsl:template>
	
	<xsl:template match="Item">
		<xsl:variable name="goal" select="../@name"/>
		<xsl:variable name="item" select="@name"/>
		<xsl:variable name="firstTime" select="@firstTime"/>
		<xsl:if test="not (preceding-sibling::Item[@name=$item and @firstTime = $firstTime and ../@name=$goal])">
			<xsl:call-template name="eol3"/>
			<xsl:text>@GoalCoverage(goal = </xsl:text>
			<xsl:value-of select="java:java-string($goal)"/>
			<xsl:text>, item=</xsl:text>
			<xsl:value-of select="java:java-string($item)"/>
			<xsl:text>, coverage={</xsl:text>
		</xsl:if>
		<xsl:value-of select="java:java-string(text())"/>
		<xsl:if test="following-sibling::Item[@name=$item and @firstTime = $firstTime and ../@name=$goal]">
			<xsl:text>, </xsl:text>
		</xsl:if>
		<xsl:if test="not (following-sibling::Item[@name=$item and @firstTime = $firstTime and ../@name=$goal])">
			<xsl:text>})</xsl:text>
			<xsl:if test="following-sibling::Item[@firstTime = $firstTime] or ../following-sibling::Goal[Item/@firstTime = $firstTime]">
				<xsl:text>,</xsl:text>
			</xsl:if>
		</xsl:if>
	</xsl:template>
	
	<xsl:template match="/">
		<xsl:apply-templates/>
	</xsl:template>
	
	<xsl:template match="Script">
	<xsl:variable name="package" select="java:substring-before-last(@id, '/')"/>
	<xsl:variable name="class" select="java:upper-first-camel-case-id(java:substring-after-last(@id, '/'))"/>
	<xsl:variable name="driverpackage" select="java:substring-before-last(@driver, '/')"/>
	<xsl:variable name="driver" select="java:upper-first-camel-case-id(java:substring-after-last(@driver, '/'))"/>
	<xsl:if test="$package != ''">
		<xsl:text>package </xsl:text>
		<xsl:value-of select="$package"/>
		<xsl:text>;</xsl:text>
		<xsl:call-template name="eol"/>
		<xsl:call-template name="eol"/>
	</xsl:if>
	<xsl:text>import static org.junit.Assert.*;
import org.junit.Test;

import org.dpolivaev.tsgen.java.Description;
import org.dpolivaev.tsgen.java.Coverage;
import org.dpolivaev.tsgen.java.GoalCoverage;

</xsl:text>
	<xsl:if test="$driverpackage != ''">
		<xsl:text>import </xsl:text>
		<xsl:value-of select="$driverpackage"/>
		<xsl:text>.</xsl:text>
		<xsl:value-of select="$driver"/>
		<xsl:text>;</xsl:text>
		<xsl:call-template name="eol"/>
		<xsl:text>import static </xsl:text>
		<xsl:value-of select="$driverpackage"/>
		<xsl:text>.</xsl:text>
		<xsl:value-of select="$driver"/>
		<xsl:text>.*;</xsl:text>
		<xsl:call-template name="eol"/>
		<xsl:call-template name="eol"/>
	</xsl:if>
	<xsl:if test="Parameter[@name='imports']">
		<xsl:value-of select="Parameter[@name='imports']"/>
		<xsl:call-template name="eol"/>
	</xsl:if>
	<xsl:text>@SuppressWarnings("unused")</xsl:text>
	<xsl:call-template name="eol"/>
	<xsl:text>public class </xsl:text>
	<xsl:value-of select="$class"/>
	<xsl:text> {</xsl:text>
	<xsl:call-template name="eol1"/>
	<xsl:value-of select="$driver"/>
	<xsl:text> driver = new </xsl:text>
	<xsl:value-of select="$driver"/>
	<xsl:text>();</xsl:text>
	<xsl:call-template name="eol1"/>
	<xsl:apply-templates select="TestCase[@id]"/>
	<xsl:text>
}
</xsl:text>
	</xsl:template>
	
	<xsl:template match="TestCase"/> 
	
	<xsl:template match="TestCase[@id]">
	<xsl:variable name="method" select="java:lower-first-camel-case-id(@id)"/>
	<xsl:apply-templates select="Description"/>
	<xsl:call-template name="Coverage"/>	
	<xsl:call-template name="eol1"/>
	<xsl:text>@Test
	public void test</xsl:text>
	<xsl:number format="001"/>
	<xsl:text>_</xsl:text>
	<xsl:value-of select="$method"/>
	<xsl:text>() throws Exception {</xsl:text>
	<xsl:call-template name="eol2"/>
	<xsl:apply-templates select="child::*[name() != 'Description' and name() != 'Goal']"/>
	<xsl:call-template name="eol1"/>
	<xsl:text>}</xsl:text>
	<xsl:call-template name="eol1"/>
	</xsl:template>
	
	<xsl:template match="ScriptPrecondition|Precondition|Focus|Verification|Postprocessing|ScriptPostprocessing">
		<xsl:variable name="method" select="java:lower-first-camel-case-id(@id)"/>
		<xsl:call-template name="eol1"/>
		<xsl:text>// </xsl:text>
		<xsl:value-of select="name()"/>
		<xsl:text> </xsl:text>
		<xsl:number/>
		<xsl:call-template name="eol2"/>
		<xsl:text>driver.</xsl:text>
		<xsl:value-of select="$method"/>
		<xsl:text>(</xsl:text>
		<xsl:apply-templates select="Parameter"/>
		<xsl:text>);</xsl:text>
	</xsl:template>
	
	<xsl:template match="Parameter">
		<xsl:if test='position()>1'>
			<xsl:text>, </xsl:text>
			<xsl:call-template name="eol3"/>
		</xsl:if>
		<xsl:text>/* </xsl:text>
		<xsl:value-of select="@name"/>
		<xsl:text>*/ </xsl:text>
		<xsl:value-of select="text()"/>
	</xsl:template>
	
	<xsl:template match="Description">
		<xsl:call-template name="eol1"/>
		<xsl:text>@Description(</xsl:text>
		<xsl:value-of select="java:java-string(text())"></xsl:value-of>
		<xsl:text>)</xsl:text>
	</xsl:template>
	
</xsl:stylesheet>