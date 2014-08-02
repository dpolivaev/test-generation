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

	<xsl:template match="/">
		<xsl:apply-templates/>
	</xsl:template>
	
	
	<xsl:template name="scriptPrecondition">
		<xsl:if test="ScriptPrecondition">
			<xsl:text>Background:</xsl:text>
			<xsl:call-template name="eol1"/>
			<xsl:apply-templates select="ScriptPrecondition"/>
			<xsl:call-template name="eol1"/>
		</xsl:if>
	</xsl:template>	
	
	<xsl:template name="scriptPostcondition">
		<xsl:if test="ScriptPostcondition">
		</xsl:if>
	</xsl:template>
	
	<xsl:template match = "Parameter[@name='feature']">
		<xsl:text>Feature: </xsl:text>
		<xsl:value-of select="."/>
		<xsl:call-template name="eol1"/>
		<xsl:call-template name="eol1"/>
	</xsl:template>	
	
	<xsl:template match="Script">
		<xsl:apply-templates select="Parameter[@name='feature']"/>
		<xsl:call-template name="scriptPrecondition"/>
		<xsl:apply-templates select="TestCase[@name]"/>
	</xsl:template>
	
	<xsl:template match="TestCase"/> 
	
	<xsl:template match="TestCase[@name]">
		<xsl:text>Scenario: </xsl:text>
		<xsl:value-of select="@name"/>
		<xsl:call-template name="eol1"/>
		<xsl:apply-templates/>
		<xsl:call-template name="eol1"/>
		<xsl:call-template name="eol1"/>
	</xsl:template>
	
	<xsl:template name="substituteParameters">
		<xsl:param name="parameterizedString"/>
		<xsl:choose>
			<xsl:when test="contains($parameterizedString, '&lt;')">
				<xsl:value-of select="substring-before($parameterizedString, '&lt;')"/>
				<xsl:value-of select="Parameter[@name=substring-after(substring-before($parameterizedString, '&gt;'), '&lt;')]"/>
				<xsl:call-template name="substituteParameters">
					<xsl:with-param name="parameterizedString" select="substring-after($parameterizedString, '&gt;')" />
				</xsl:call-template>
			</xsl:when>
			<xsl:otherwise>
				<xsl:value-of select="$parameterizedString"/>
			</xsl:otherwise>
		</xsl:choose>
	</xsl:template>

	<xsl:template name="scenarioPart">
		<xsl:param name="keyword"/>
		<xsl:variable name="element" select="name()"/>
		<xsl:text>    </xsl:text>
		<xsl:choose>
			<xsl:when test="preceding-sibling::*[name() = $element]"><xsl:text>And</xsl:text></xsl:when>
			<xsl:otherwise><xsl:value-of select="$keyword"/></xsl:otherwise>
		</xsl:choose>
		<xsl:text> </xsl:text>
		<xsl:call-template name="substituteParameters">
			<xsl:with-param name="parameterizedString" select="@step"/>
		</xsl:call-template>
		<xsl:call-template name="eol1"/>
	</xsl:template>
		
	<xsl:template match="ScriptPrecondition|Precondition">
		<xsl:call-template name="scenarioPart">
			<xsl:with-param name="keyword" select="'Given'"/>
		</xsl:call-template>
	</xsl:template>
	
	<xsl:template match="Focus">
		<xsl:call-template name="scenarioPart">
			<xsl:with-param name="keyword" select="'When'"/>
		</xsl:call-template>
	</xsl:template>
	
	<xsl:template match="Verification">
		<xsl:call-template name="scenarioPart">
			<xsl:with-param name="keyword" select="'Then'"/>
		</xsl:call-template>
	</xsl:template>
	
</xsl:stylesheet>