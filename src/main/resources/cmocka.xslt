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
	
	<xsl:template name="driver">
		<xsl:value-of select="java:snake-lower-case-id(java:substring-after-last(ancestor-or-self::Script/@driver, '/'))"/>
	</xsl:template>	
	
	<xsl:template name="driver-directory">
		<xsl:variable name="directory" select="java:substring-before-last(ancestor-or-self::Script/@driver, '/')"/>
		<xsl:if test="$directory != ''">
			<xsl:value-of select="$directory"/>
			<xsl:text>/</xsl:text>
		</xsl:if>
	</xsl:template>	
	
	<xsl:template name="Coverage">
		<xsl:if test="Goal">
			<xsl:call-template name="eol"/>
			<xsl:if test="Goal/Item[@firstTime='true']">
				<xsl:call-template name="eol"/>
				<xsl:text>\par First Hit Coverage:</xsl:text>
				<xsl:apply-templates select="Goal/Item[@firstTime='true']">
					<xsl:sort select="../@name"/>
					<xsl:sort select="@name"/>
				</xsl:apply-templates>
				<xsl:call-template name="eol"/>
			</xsl:if>
			<xsl:if test="Goal/Item[@firstTime='false']">
				<xsl:call-template name="eol"/>
				<xsl:text>\par Next Hit Coverage:</xsl:text>
				<xsl:apply-templates select="Goal/Item[@firstTime='false']">
					<xsl:sort select="../@name"/>
					<xsl:sort select="@name"/>
				</xsl:apply-templates>
				<xsl:call-template name="eol"/>
			</xsl:if>
		</xsl:if>
	</xsl:template>
	
	<xsl:template match="Item">
		<xsl:variable name="goal" select="../@name"/>
		<xsl:variable name="item" select="@name"/>
		<xsl:variable name="firstTime" select="@firstTime"/>
		<xsl:if test="not (preceding-sibling::Item[@name=$item and @firstTime = $firstTime and ../@name=$goal])">
			<xsl:call-template name="eol3"/>
			<xsl:text>goal = </xsl:text>
			<xsl:value-of select="$goal"/>
			<xsl:text>, item=</xsl:text>
			<xsl:value-of select="$item"/>
			<xsl:text>, coverage=</xsl:text>
		</xsl:if>
		<xsl:value-of select="text()"/>
		<xsl:if test="following-sibling::Item[@name=$item and @firstTime = $firstTime and ../@name=$goal]">
			<xsl:text>, </xsl:text>
		</xsl:if>
		<xsl:if test="not (following-sibling::Item[@name=$item and @firstTime = $firstTime and ../@name=$goal])">
			<xsl:if test="following-sibling::Item[@name=$item and @firstTime = $firstTime and ../@name=$goal]">
				<xsl:text>,</xsl:text>
			</xsl:if>
		</xsl:if>
	</xsl:template>
	
	<xsl:template match="/">
		<xsl:apply-templates/>
	</xsl:template>
	
	<xsl:template match="Script">
	<xsl:variable name="file" select="java:snake-lower-case-id(@id)"/>
	<xsl:text>#include &lt;stdarg.h&gt;
#include &lt;stddef.h&gt;
#include &lt;setjmp.h&gt;
#include &lt;cmocka.h&gt;

</xsl:text>

	<xsl:text>#include "</xsl:text>
	<xsl:call-template name="driver-directory"/>
	<xsl:call-template name="driver"/>
	<xsl:text>.h"</xsl:text>
	<xsl:call-template name="eol"/>
	<xsl:call-template name="eol"/>
	<xsl:text>static void global_precondition() {</xsl:text>
	<xsl:call-template name="eol"/>
	<xsl:apply-templates select="ScriptPrecondition"/>
	<xsl:text>
}

static void global_postprocessing(){</xsl:text>
	<xsl:call-template name="eol"/>
	<xsl:apply-templates select="ScriptPostprocessing"/>
	<xsl:text>
}

</xsl:text>
	<xsl:apply-templates select="TestCase[@id]"/>
	<xsl:call-template name="eol"/>
	<xsl:call-template name="eol"/>
	<xsl:text>int main(void) {
    int rc;
    const UnitTest tests[] = {</xsl:text>
    <xsl:for-each select="TestCase[@id]">    
	    <xsl:call-template name="eol2"/>   
	    <xsl:text>unit_test(</xsl:text>
	    <xsl:call-template name="testCaseName"/>
	    <xsl:text>),</xsl:text>
	</xsl:for-each>
	<xsl:call-template name="eol1"/>
	<xsl:text>};
	global_precondition();
    rc = run_tests(tests);
    global_postprocessing();
    return rc;
}
</xsl:text>
	</xsl:template>
	
	<xsl:template match="TestCase"/> 
	
	<xsl:template name="testCaseName">
		<xsl:variable name="method" select="java:snake-lower-case-id(@id)"/>
		<xsl:text>test</xsl:text>
		<xsl:number format="001"/>
		<xsl:text>_</xsl:text>
		<xsl:value-of select="$method"/>
	</xsl:template>
	
	<xsl:template match="TestCase[@id]">
		<xsl:text>/*!</xsl:text>
		<xsl:call-template name="eol"/>
		<xsl:apply-templates select="Description"/>
		<xsl:call-template name="Coverage"/>	
		<xsl:text>*/</xsl:text>
		<xsl:call-template name="eol"/>
		<xsl:text>static void </xsl:text>
		<xsl:call-template name="testCaseName"/>
		<xsl:text>() {</xsl:text>
		<xsl:apply-templates select="child::*[name() != 'Description' and name() != 'Goal']"/>
		<xsl:call-template name="eol"/>
		<xsl:text>}</xsl:text>
		<xsl:call-template name="eol"/>
	</xsl:template>
	
	<xsl:template match="ScriptPrecondition|Precondition|Focus|Verification|Postprocessing|ScriptPostprocessing">
		<xsl:variable name="method" select="java:snake-lower-case-id(@id)"/>
		<xsl:call-template name="eol1"/>
		<xsl:text>// </xsl:text>
		<xsl:value-of select="name()"/>
		<xsl:text> </xsl:text>
		<xsl:number/>
		<xsl:call-template name="eol2"/>
		<xsl:call-template name="driver"/>
		<xsl:text>_</xsl:text>
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
		<xsl:value-of select="java:snake-lower-case-id(@name)"/>
		<xsl:text>*/ </xsl:text>
		<xsl:choose>
			<xsl:when test="number(text()) = text() or starts-with(text(), '&quot;') or contains(text(), '(')">
				<xsl:value-of select="text()"/>
			</xsl:when>
			<xsl:otherwise>
				<xsl:value-of select="java:snake-upper-case-id(text())"/>
			</xsl:otherwise>
		</xsl:choose>
	</xsl:template>
	
	<xsl:template match="Description">
		<xsl:value-of select="text()"></xsl:value-of>
	</xsl:template>
</xsl:stylesheet>