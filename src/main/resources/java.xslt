<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
xmlns:java="http://www.oracle.com/XSL/Transform/java/org.dpolivaev.tsgen.java.TransformationHelper"
>
<xsl:output method="text"/>
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

	<xsl:template match="/">
		<xsl:apply-templates/>
	</xsl:template>
	
	<xsl:template match="Script">
		<xsl:text>import static org.junit.Assert.*;

import org.junit.Test;


public class </xsl:text>
	<xsl:variable name="class" select="java:upper-case-java-id(@id)"/>
	<xsl:value-of select="$class"/>
	<xsl:text> {</xsl:text>
	<xsl:call-template name="eol1"/>
	<xsl:value-of select="$class"/>
	<xsl:text>Driver driver = new </xsl:text>
	<xsl:value-of select="$class"/>
	<xsl:text>Driver();</xsl:text>
	<xsl:apply-templates/>
	<xsl:text>
}
</xsl:text>
	</xsl:template>
	
	<xsl:template match="TestCase"/> 
	
	<xsl:template match="TestCase[@id]">
	<xsl:variable name="method" select="java:lower-case-java-id(@id)"/>
	<xsl:apply-templates select="Description"/>
	
	<xsl:call-template name="eol1"/>
	<xsl:text>@Test
	public void test</xsl:text>
	<xsl:number format="001"/>
	<xsl:text>_</xsl:text>
	<xsl:value-of select="$method"/>
	<xsl:text>() {</xsl:text>
	<xsl:call-template name="eol2"/>
	<xsl:apply-templates select="child::*[name() != 'Description' and name() != 'Requirement']"/>
	<xsl:call-template name="eol1"/>
	<xsl:text>}</xsl:text>
	<xsl:call-template name="eol2"/>
	</xsl:template>
	
	<xsl:template match="Precondition|EnterState|PreconditionInState|Focus|VerificationInState|CheckState|Verification|Postprocessing">
		<xsl:variable name="method" select="java:lower-case-java-id(@id)"/>
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