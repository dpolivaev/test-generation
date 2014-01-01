<map version="freeplane 1.2.0">
<!--To view this file, download free mind mapping software Freeplane from http://freeplane.sourceforge.net -->
<node TEXT="Generator Test" FOLDED="false" ID="ID_430141275" CREATED="1358771598636" MODIFIED="1358771626646"><hook NAME="MapStyle" background="#e7e7e7">
    <conditional_styles>
        <conditional_style ACTIVE="true" STYLE_REF="Requirement" LAST="true">
            <script_condition SCRIPT="def isRequirement(n){&#xd;&#xa;    println (n)&#xd;&#xa;    if(n.text.startsWith(&quot;[&quot;))&#xd;&#xa;        true&#xd;&#xa;    else if(n.root)&#xd;&#xa;        false     &#xd;&#xa;    else {&#xd;&#xa;        parent = n.parent&#xd;&#xa;        isRequirement(parent)    &#xd;&#xa;    }&#xd;&#xa;}&#xd;&#xa;&#xd;&#xa;isRequirement(node)"/>
        </conditional_style>
        <conditional_style ACTIVE="true" LOCALIZED_STYLE_REF="styles.connection" LAST="true">
            <disjunct_condition>
                <node_compare_condition VALUE="=" MATCH_CASE="false" MATCH_APPROXIMATELY="false" COMPARATION_RESULT="0" SUCCEED="true" ITEM="filter_node"/>
                <node_compare_condition VALUE=":" MATCH_CASE="false" MATCH_APPROXIMATELY="false" COMPARATION_RESULT="0" SUCCEED="true" ITEM="filter_node"/>
                <node_compare_condition VALUE="shuffled" MATCH_CASE="false" MATCH_APPROXIMATELY="false" COMPARATION_RESULT="0" SUCCEED="true" ITEM="filter_node"/>
                <node_compare_condition VALUE="loops" MATCH_CASE="false" MATCH_APPROXIMATELY="false" COMPARATION_RESULT="0" SUCCEED="true" ITEM="filter_node"/>
                <node_compare_condition VALUE="defaults" MATCH_CASE="false" MATCH_APPROXIMATELY="false" COMPARATION_RESULT="0" SUCCEED="true" ITEM="filter_node"/>
                <node_compare_condition VALUE="code" MATCH_CASE="false" MATCH_APPROXIMATELY="false" COMPARATION_RESULT="0" SUCCEED="true" ITEM="filter_node"/>
                <node_compare_condition VALUE="when" MATCH_CASE="false" MATCH_APPROXIMATELY="false" COMPARATION_RESULT="0" SUCCEED="true" ITEM="filter_node"/>
                <node_compare_condition VALUE="datatree" MATCH_CASE="false" MATCH_APPROXIMATELY="false" COMPARATION_RESULT="0" SUCCEED="true" ITEM="filter_node"/>
                <node_compare_condition VALUE="strategy" MATCH_CASE="false" MATCH_APPROXIMATELY="false" COMPARATION_RESULT="0" SUCCEED="true" ITEM="filter_node"/>
                <node_compare_condition VALUE="property" MATCH_CASE="false" MATCH_APPROXIMATELY="false" COMPARATION_RESULT="0" SUCCEED="true" ITEM="filter_node"/>
                <node_compare_condition VALUE="disable" MATCH_CASE="false" MATCH_APPROXIMATELY="false" COMPARATION_RESULT="0" SUCCEED="true" ITEM="filter_node"/>
                <node_compare_condition VALUE="skip" MATCH_CASE="false" MATCH_APPROXIMATELY="false" COMPARATION_RESULT="0" SUCCEED="true" ITEM="filter_node"/>
                <node_compare_condition VALUE="goals" MATCH_CASE="false" MATCH_APPROXIMATELY="false" COMPARATION_RESULT="0" SUCCEED="true" ITEM="filter_any_text"/>
                <node_compare_condition VALUE="goals" MATCH_CASE="true" MATCH_APPROXIMATELY="false" COMPARATION_RESULT="0" SUCCEED="true" ITEM="filter_node"/>
                <node_compare_condition VALUE="ordered" MATCH_CASE="false" MATCH_APPROXIMATELY="false" COMPARATION_RESULT="0" SUCCEED="true" ITEM="filter_node"/>
            </disjunct_condition>
        </conditional_style>
        <conditional_style ACTIVE="true" LOCALIZED_STYLE_REF="styles.topic" LAST="true">
            <disjunct_condition>
                <node_compare_condition VALUE="if" MATCH_CASE="false" MATCH_APPROXIMATELY="false" COMPARATION_RESULT="0" SUCCEED="true" ITEM="filter_node"/>
                <node_compare_condition VALUE="if" MATCH_CASE="false" MATCH_APPROXIMATELY="false" COMPARATION_RESULT="0" SUCCEED="true" ITEM="filter_parent"/>
            </disjunct_condition>
        </conditional_style>
        <conditional_style ACTIVE="true" LOCALIZED_STYLE_REF="styles.list" LAST="true">
            <disjunct_condition>
                <node_compare_condition VALUE="=" MATCH_CASE="false" MATCH_APPROXIMATELY="false" COMPARATION_RESULT="0" SUCCEED="true" ITEM="filter_parent"/>
                <node_compare_condition VALUE="code" MATCH_CASE="false" MATCH_APPROXIMATELY="false" COMPARATION_RESULT="0" SUCCEED="true" ITEM="filter_parent"/>
                <node_compare_condition VALUE=":" MATCH_CASE="false" MATCH_APPROXIMATELY="false" COMPARATION_RESULT="0" SUCCEED="true" ITEM="filter_parent"/>
            </disjunct_condition>
        </conditional_style>
        <conditional_style ACTIVE="true" LOCALIZED_STYLE_REF="styles.subsubtopic" LAST="true">
            <node_compare_condition VALUE="when" MATCH_CASE="false" MATCH_APPROXIMATELY="false" COMPARATION_RESULT="0" SUCCEED="true" ITEM="filter_parent"/>
        </conditional_style>
    </conditional_styles>

<map_styles>
<stylenode LOCALIZED_TEXT="styles.root_node">
<stylenode LOCALIZED_TEXT="styles.predefined" POSITION="right">
<stylenode LOCALIZED_TEXT="default" STYLE="fork" FORMAT="NO_FORMAT" MAX_WIDTH="600">
<font NAME="Arial" SIZE="10" BOLD="false"/>
<edge STYLE="bezier" COLOR="#808080" WIDTH="thin"/>
</stylenode>
<stylenode LOCALIZED_TEXT="defaultstyle.details"/>
<stylenode LOCALIZED_TEXT="defaultstyle.note"/>
<stylenode LOCALIZED_TEXT="defaultstyle.floating">
<edge STYLE="hide_edge"/>
<cloud COLOR="#f0f0f0" SHAPE="ROUND_RECT"/>
</stylenode>
<stylenode LOCALIZED_TEXT="styles.ok">
<icon BUILTIN="button_ok"/>
</stylenode>
<stylenode LOCALIZED_TEXT="styles.needs_action">
<icon BUILTIN="messagebox_warning"/>
</stylenode>
<stylenode LOCALIZED_TEXT="styles.floating_node">
<cloud COLOR="#ffffff" SHAPE="ARC"/>
<edge STYLE="hide_edge"/>
</stylenode>
<stylenode LOCALIZED_TEXT="styles.topic" COLOR="#009900" STYLE="fork">
<font NAME="Liberation Sans" SIZE="12" BOLD="false"/>
<edge STYLE="bezier" COLOR="#808080" WIDTH="thin"/>
</stylenode>
<stylenode LOCALIZED_TEXT="styles.subtopic" COLOR="#cc3300" STYLE="fork">
<font NAME="Liberation Sans" SIZE="12" BOLD="true"/>
<edge STYLE="bezier" COLOR="#808080" WIDTH="thin"/>
</stylenode>
<stylenode LOCALIZED_TEXT="styles.subsubtopic" COLOR="#006666">
<font NAME="Liberation Sans" SIZE="10"/>
</stylenode>
<stylenode LOCALIZED_TEXT="styles.connection" COLOR="#0033cc" STYLE="fork">
<font NAME="Dialog" SIZE="11" BOLD="true"/>
</stylenode>
<stylenode LOCALIZED_TEXT="styles.important" COLOR="#ff0000">
<icon BUILTIN="yes"/>
<font NAME="Liberation Sans" SIZE="12"/>
</stylenode>
<stylenode LOCALIZED_TEXT="styles.question">
<icon BUILTIN="help"/>
<font NAME="Aharoni" SIZE="12"/>
</stylenode>
<stylenode LOCALIZED_TEXT="styles.key" COLOR="#996600">
<icon BUILTIN="password"/>
<font NAME="Liberation Sans" SIZE="12" BOLD="false"/>
</stylenode>
<stylenode LOCALIZED_TEXT="styles.idea">
<icon BUILTIN="idea"/>
</stylenode>
<stylenode LOCALIZED_TEXT="styles.note" COLOR="#990000">
<font NAME="Liberation Sans" SIZE="12"/>
</stylenode>
<stylenode LOCALIZED_TEXT="styles.date" COLOR="#0033ff">
<icon BUILTIN="calendar"/>
<font NAME="Liberation Sans" SIZE="12"/>
</stylenode>
<stylenode LOCALIZED_TEXT="styles.website" COLOR="#006633">
<font NAME="Liberation Sans" SIZE="12"/>
</stylenode>
<stylenode LOCALIZED_TEXT="styles.list" COLOR="#660066" FORMAT="NO_FORMAT" MAX_WIDTH="200">
<font NAME="Times New Roman" SIZE="9" BOLD="false"/>
</stylenode>
<stylenode LOCALIZED_TEXT="styles.quotation" COLOR="#338800" STYLE="fork">
<font NAME="Liberation Sans" SIZE="12" BOLD="false" ITALIC="false"/>
<edge STYLE="bezier" COLOR="#808080" WIDTH="thin"/>
</stylenode>
<stylenode LOCALIZED_TEXT="styles.definition" COLOR="#666600">
<font NAME="Liberation Sans" SIZE="12" BOLD="false"/>
</stylenode>
<stylenode LOCALIZED_TEXT="styles.description" COLOR="#996600">
<font NAME="Liberation Sans" SIZE="12" BOLD="false"/>
</stylenode>
<stylenode LOCALIZED_TEXT="styles.pending" COLOR="#b3b95c">
<font NAME="Liberation Sans" SIZE="12"/>
</stylenode>
</stylenode>
<stylenode LOCALIZED_TEXT="styles.AutomaticLayout" POSITION="right">
<stylenode LOCALIZED_TEXT="AutomaticLayout.level.root" COLOR="#000000">
<font SIZE="20"/>
</stylenode>
<stylenode LOCALIZED_TEXT="AutomaticLayout.level,1" COLOR="#0033ff">
<font SIZE="18"/>
<edge WIDTH="8"/>
</stylenode>
<stylenode LOCALIZED_TEXT="AutomaticLayout.level,2" COLOR="#00b439">
<font SIZE="16"/>
<edge STYLE="bezier" WIDTH="thin"/>
</stylenode>
<stylenode LOCALIZED_TEXT="AutomaticLayout.level,3" COLOR="#990000">
<font SIZE="14"/>
</stylenode>
<stylenode LOCALIZED_TEXT="AutomaticLayout.level,4" COLOR="#111111">
<font SIZE="12"/>
</stylenode>
</stylenode>
<stylenode LOCALIZED_TEXT="styles.user-defined" POSITION="right">
<stylenode TEXT="Requirement" COLOR="#666666">
<font SIZE="8"/>
</stylenode>
</stylenode>
</stylenode>
</map_styles>
</hook>
<node TEXT="strategy" POSITION="right" ID="ID_939308960" CREATED="1358771626886" MODIFIED="1358771637145">
<node TEXT="GeneratorTest" ID="ID_16927754" CREATED="1358773692030" MODIFIED="1358773698335">
<node TEXT="loops" ID="ID_730412874" CREATED="1358771656760" MODIFIED="1358771658618">
<node TEXT="$topologies" ID="ID_372813298" CREATED="1358771647369" MODIFIED="1358772885889">
<node TEXT=":" ID="ID_1167210425" CREATED="1358771653726" MODIFIED="1358771654508">
<node TEXT="&apos;stand alone property&apos;" ID="ID_337692473" CREATED="1358771666299" MODIFIED="1358771685224">
<node TEXT="$x" ID="ID_1901490951" CREATED="1358771686447" MODIFIED="1358771690739">
<node TEXT=":" ID="ID_1608334455" CREATED="1358771694145" MODIFIED="1358771696269">
<node TEXT="&apos;a&apos;, &apos;b&apos;, &apos;c&apos;" ID="ID_112204457" CREATED="1358771696930" MODIFIED="1358774155222"/>
</node>
</node>
</node>
<node TEXT="&apos;all combinations 1&apos;" ID="ID_1947416665" CREATED="1358771998793" MODIFIED="1358772446914">
<node TEXT="$x" ID="ID_382077686" CREATED="1358771686447" MODIFIED="1358771690739">
<node TEXT=":" ID="ID_599349226" CREATED="1358771694145" MODIFIED="1358771696269">
<node TEXT="&apos;a&apos;, &apos;b&apos;, &apos;c&apos;" ID="ID_1188176978" CREATED="1358771696930" MODIFIED="1358774155237">
<node TEXT="$y" ID="ID_1093485382" CREATED="1358771686447" MODIFIED="1358772033831">
<node TEXT=":" ID="ID_572971606" CREATED="1358771694145" MODIFIED="1358771696269">
<node TEXT="&apos;A&apos;, &apos;B&apos;, &apos;C&apos;" ID="ID_14211487" CREATED="1358771696930" MODIFIED="1358774155253"/>
</node>
</node>
</node>
</node>
</node>
</node>
<node TEXT="&apos;all combinations 2&apos;" ID="ID_1841872494" CREATED="1358771998793" MODIFIED="1358772459901">
<node TEXT="$x" ID="ID_691287596" CREATED="1358771686447" MODIFIED="1358771690739">
<node TEXT=":" ID="ID_415086003" CREATED="1358771694145" MODIFIED="1358771696269">
<node TEXT="&apos;a&apos;" ID="ID_22909114" CREATED="1358771696930" MODIFIED="1358774155253"/>
<node TEXT="&apos;b&apos;" ID="ID_1249849346" CREATED="1358772477840" MODIFIED="1358774155268"/>
<node TEXT="&apos;c&apos;" ID="ID_446521777" CREATED="1358772482684" MODIFIED="1358774155284"/>
</node>
<node TEXT="$y" ID="ID_279012600" CREATED="1358771686447" MODIFIED="1358772033831">
<node TEXT=":" ID="ID_520799135" CREATED="1358771694145" MODIFIED="1358771696269">
<node TEXT="&apos;A&apos;, &apos;B&apos;, &apos;C&apos;" ID="ID_333429188" CREATED="1358771696930" MODIFIED="1358774155284"/>
</node>
</node>
</node>
</node>
<node TEXT="&apos;parallel combinations ordered&apos;" ID="ID_701169002" CREATED="1358772114557" MODIFIED="1358772129160">
<node TEXT="$x" ID="ID_54594332" CREATED="1358771686447" MODIFIED="1358771690739">
<node TEXT=":" ID="ID_1443588481" CREATED="1358771694145" MODIFIED="1358771696269">
<node TEXT="&apos;a&apos;, &apos;b&apos;, &apos;c&apos;" ID="ID_750468740" CREATED="1358771696930" MODIFIED="1358774155284">
<node TEXT="ordered" ID="ID_884941723" CREATED="1358772138975" MODIFIED="1358772140974"/>
</node>
</node>
</node>
<node TEXT="$y" ID="ID_681251975" CREATED="1358771686447" MODIFIED="1358772033831">
<node TEXT=":" ID="ID_276685470" CREATED="1358771694145" MODIFIED="1358771696269">
<node TEXT="&apos;A&apos;, &apos;B&apos;" ID="ID_493473693" CREATED="1358771696930" MODIFIED="1358774155284">
<node TEXT="ordered" ID="ID_924871443" CREATED="1358772138975" MODIFIED="1358772140974"/>
</node>
</node>
</node>
<node TEXT="$z" ID="ID_1250859565" CREATED="1358773349709" MODIFIED="1358773351925">
<node TEXT=":" ID="ID_1172228694" CREATED="1358773352649" MODIFIED="1358773353914">
<node TEXT="&apos;1&apos;, &apos;2&apos;, &apos;3&apos;, &apos;4&apos;, &apos;5&apos;" ID="ID_344874565" CREATED="1358771696930" MODIFIED="1358774155284">
<node TEXT="ordered" ID="ID_1547227400" CREATED="1358772138975" MODIFIED="1358772140974"/>
</node>
</node>
</node>
</node>
<node TEXT="&apos;parallel combinations shuffled&apos;" ID="ID_1622863647" CREATED="1358772114557" MODIFIED="1358772205855">
<node TEXT="$x" ID="ID_1717758293" CREATED="1358771686447" MODIFIED="1358771690739">
<node TEXT=":" ID="ID_1499402902" CREATED="1358771694145" MODIFIED="1358771696269">
<node TEXT="&apos;a&apos;, &apos;b&apos;, &apos;c&apos;" ID="ID_143382281" CREATED="1358771696930" MODIFIED="1358774155300">
<node TEXT="shuffled" ID="ID_505554114" CREATED="1358772138975" MODIFIED="1358772209989"/>
</node>
</node>
</node>
<node TEXT="$y" ID="ID_951272791" CREATED="1358771686447" MODIFIED="1358772033831">
<node TEXT=":" ID="ID_1448244599" CREATED="1358771694145" MODIFIED="1358771696269">
<node TEXT="&apos;A&apos;, &apos;B&apos;" ID="ID_737590493" CREATED="1358771696930" MODIFIED="1358774155300">
<node TEXT="shuffled" ID="ID_1433799541" CREATED="1358772138975" MODIFIED="1358772209989"/>
</node>
</node>
</node>
<node TEXT="$z" ID="ID_1488605471" CREATED="1358773349709" MODIFIED="1358773351925">
<node TEXT=":" ID="ID_907760695" CREATED="1358773352649" MODIFIED="1358773353914">
<node TEXT="&apos;1&apos;, &apos;2&apos;, &apos;3&apos;, &apos;4&apos;, &apos;5&apos;" ID="ID_969503409" CREATED="1358771696930" MODIFIED="1358774155300">
<node TEXT="shuffled" ID="ID_1889782559" CREATED="1358772138975" MODIFIED="1358772209989"/>
</node>
</node>
</node>
</node>
<node TEXT="&apos;dependent values&apos;" ID="ID_1482639930" CREATED="1358772301365" MODIFIED="1358772314813">
<node TEXT="$x" ID="ID_4096789" CREATED="1358771686447" MODIFIED="1358771690739">
<node TEXT=":" ID="ID_1805632336" CREATED="1358771694145" MODIFIED="1358771696269">
<node TEXT="&apos;a&apos;, &apos;b&apos;, &apos;c&apos;" ID="ID_1106947332" CREATED="1358771696930" MODIFIED="1358774155300">
<node TEXT="ordered" ID="ID_153084992" CREATED="1358772138975" MODIFIED="1358772140974"/>
</node>
</node>
</node>
<node TEXT="$y" ID="ID_1070161483" CREATED="1358771686447" MODIFIED="1358772033831">
<node TEXT=":" ID="ID_422838156" CREATED="1358771694145" MODIFIED="1358771696269">
<node TEXT="&apos;A&apos;, &apos;B&apos;" ID="ID_184618937" CREATED="1358771696930" MODIFIED="1358774155315">
<node TEXT="ordered" ID="ID_958196105" CREATED="1358772138975" MODIFIED="1358772140974"/>
</node>
</node>
</node>
<node TEXT="$z" ID="ID_878343963" CREATED="1358772326270" MODIFIED="1358772333089">
<node TEXT=":" ID="ID_1893636394" CREATED="1358772333735" MODIFIED="1358772335826">
<node TEXT="$x # &quot; &amp;&amp; &quot; # $y ,  $x # &quot; || &quot; # $y" ID="ID_259404688" CREATED="1358772362799" MODIFIED="1358774182826"/>
</node>
</node>
</node>
<node TEXT="&apos;explicit dependencies&apos;" ID="ID_1243712791" CREATED="1358772301365" MODIFIED="1358772434762">
<node TEXT="when" ID="ID_1736114420" CREATED="1358772591133" MODIFIED="1358772592960">
<node TEXT="$x, $y" ID="ID_256359275" CREATED="1358772593793" MODIFIED="1358772597928">
<node TEXT="$z" ID="ID_213227339" CREATED="1358772326270" MODIFIED="1358772333089">
<node TEXT=":" ID="ID_600605047" CREATED="1358772333735" MODIFIED="1358772335826">
<node TEXT="$x # &quot; &amp;&amp; &quot; # $y ,  $x # &quot; || &quot; # $y" ID="ID_1772757062" CREATED="1358772362799" MODIFIED="1358774182873"/>
</node>
</node>
</node>
</node>
<node TEXT="$x" ID="ID_1564268575" CREATED="1358771686447" MODIFIED="1358771690739">
<node TEXT=":" ID="ID_1222903894" CREATED="1358771694145" MODIFIED="1358771696269">
<node TEXT="&apos;a&apos;, &apos;b&apos;, &apos;c&apos;" ID="ID_1035418632" CREATED="1358771696930" MODIFIED="1358774155315"/>
</node>
</node>
<node TEXT="$y" ID="ID_1320065543" CREATED="1358771686447" MODIFIED="1358772033831">
<node TEXT=":" ID="ID_1558778707" CREATED="1358771694145" MODIFIED="1358771696269">
<node TEXT="&apos;A&apos;, &apos;B&apos;" ID="ID_1886162903" CREATED="1358771696930" MODIFIED="1358774155315"/>
</node>
</node>
</node>
<node TEXT="&apos;branches&apos;" ID="ID_1862971163" CREATED="1358772061575" MODIFIED="1358772069750">
<node TEXT="$x" ID="ID_863481911" CREATED="1358771686447" MODIFIED="1358771690739">
<node TEXT=":" ID="ID_1218497395" CREATED="1358771694145" MODIFIED="1358771696269">
<node TEXT="&apos;a&apos;, &apos;b&apos;" ID="ID_1102349941" CREATED="1358771696930" MODIFIED="1358774155331">
<node TEXT="$y" ID="ID_1846845471" CREATED="1358771686447" MODIFIED="1358772033831">
<node TEXT=":" ID="ID_579890334" CREATED="1358771694145" MODIFIED="1358771696269">
<node TEXT="&apos;A&apos;, &apos;B&apos;" ID="ID_590607450" CREATED="1358771696930" MODIFIED="1358774155331"/>
</node>
</node>
</node>
<node TEXT="&apos;c&apos;" ID="ID_1085698967" CREATED="1358772079737" MODIFIED="1358774155331">
<node TEXT="$y" ID="ID_405214829" CREATED="1358771686447" MODIFIED="1358772033831">
<node TEXT=":" ID="ID_736257665" CREATED="1358771694145" MODIFIED="1358771696269">
<node TEXT="&apos;C&apos;" ID="ID_237543830" CREATED="1358772090433" MODIFIED="1358774155331"/>
</node>
</node>
</node>
</node>
</node>
</node>
<node TEXT="&apos;conditions&apos;" ID="ID_595928524" CREATED="1358772504892" MODIFIED="1358772509916">
<node TEXT="$x" ID="ID_1178634409" CREATED="1358771686447" MODIFIED="1358771690739">
<node TEXT=":" ID="ID_40386127" CREATED="1358771694145" MODIFIED="1358771696269">
<node TEXT="&apos;a&apos;, &apos;b&apos;" ID="ID_180154447" CREATED="1358771696930" MODIFIED="1358774155346"/>
<node TEXT="&apos;c&apos;" ID="ID_23379331" CREATED="1358772079737" MODIFIED="1358774155346"/>
</node>
<node TEXT="$y" ID="ID_1293568196" CREATED="1358771686447" MODIFIED="1358772033831">
<node TEXT=":" ID="ID_1272654693" CREATED="1358771694145" MODIFIED="1358771696269">
<node TEXT="&apos;C&apos;" ID="ID_970729398" CREATED="1358772090433" MODIFIED="1358774155346"/>
</node>
<node TEXT="if" ID="ID_402415809" CREATED="1358772543811" MODIFIED="1358772547790">
<node TEXT="$x == &apos;c&apos;" ID="ID_1364979951" CREATED="1358772548717" MODIFIED="1358774155346">
<node TEXT=":" ID="ID_1508125874" CREATED="1358771694145" MODIFIED="1358771696269">
<node TEXT="&apos;A&apos;, &apos;B&apos;" ID="ID_406125654" CREATED="1358771696930" MODIFIED="1358774155362"/>
</node>
</node>
</node>
</node>
</node>
</node>
<node TEXT="&apos;defaults&apos;" ID="ID_1104656180" CREATED="1358772984779" MODIFIED="1358772991830">
<node TEXT="$x" ID="ID_166508851" CREATED="1358771686447" MODIFIED="1358771690739">
<node TEXT=":" ID="ID_637288477" CREATED="1358771694145" MODIFIED="1358771696269">
<node TEXT="&apos;a&apos;, &apos;b&apos;, &apos;c&apos;" ID="ID_1756799131" CREATED="1358771696930" MODIFIED="1358774155362"/>
<node TEXT="&apos;d&apos;" ID="ID_411885968" CREATED="1358772079737" MODIFIED="1358774155362">
<node TEXT="$y" ID="ID_721202074" CREATED="1358773039601" MODIFIED="1358773044126">
<node TEXT=":" ID="ID_1613959208" CREATED="1358773044632" MODIFIED="1358773045865">
<node TEXT="&apos;A&apos;, &apos;B&apos;" ID="ID_1886963509" CREATED="1358773046231" MODIFIED="1358774155362"/>
</node>
</node>
</node>
</node>
<node TEXT="defaults" ID="ID_534578560" CREATED="1358773127405" MODIFIED="1358773130012">
<node TEXT="$y" ID="ID_127561000" CREATED="1358771686447" MODIFIED="1358772033831">
<node TEXT="=" ID="ID_1970788549" CREATED="1358771694145" MODIFIED="1358773955821">
<node TEXT="&apos;C&apos;" ID="ID_1980195006" CREATED="1358772090433" MODIFIED="1358774155378"/>
</node>
<node TEXT="if" ID="ID_1899585430" CREATED="1358772543811" MODIFIED="1358772547790">
<node TEXT="$x != &apos;b&apos;" ID="ID_1432118633" CREATED="1358772548717" MODIFIED="1358774155378">
<node TEXT="=" ID="ID_1156167680" CREATED="1358771694145" MODIFIED="1358773081800">
<node TEXT="&apos;D&apos;, &apos;E&apos;" ID="ID_544854019" CREATED="1358771696930" MODIFIED="1358774155378"/>
</node>
</node>
</node>
</node>
</node>
</node>
</node>
<node TEXT="&apos;branched defaults&apos;" ID="ID_1070860863" CREATED="1358772984779" MODIFIED="1358773186160">
<node TEXT="$x" ID="ID_1206189449" CREATED="1358771686447" MODIFIED="1358771690739">
<node TEXT=":" ID="ID_968369343" CREATED="1358771694145" MODIFIED="1358771696269">
<node TEXT="&apos;a&apos;, &apos;b&apos;, &apos;c&apos;" ID="ID_1417835571" CREATED="1358771696930" MODIFIED="1358774155378"/>
<node TEXT="&apos;d&apos;" ID="ID_1667904819" CREATED="1358772079737" MODIFIED="1358774155393">
<node TEXT="$y" ID="ID_350872222" CREATED="1358773039601" MODIFIED="1358773044126">
<node TEXT=":" ID="ID_1399370689" CREATED="1358773044632" MODIFIED="1358773045865">
<node TEXT="&apos;A&apos;, &apos;B&apos;" ID="ID_577623327" CREATED="1358773046231" MODIFIED="1358774155393"/>
</node>
</node>
</node>
<node TEXT="&apos;e&apos;" ID="ID_1659526327" CREATED="1358773134362" MODIFIED="1358774155393">
<node TEXT="defaults" ID="ID_1840453462" CREATED="1358773127405" MODIFIED="1358773130012">
<node TEXT="$y" ID="ID_1270265717" CREATED="1358771686447" MODIFIED="1358772033831">
<node TEXT="=" ID="ID_781733906" CREATED="1358771694145" MODIFIED="1358773750462">
<node TEXT="&apos;E&apos;" ID="ID_1094976230" CREATED="1358772090433" MODIFIED="1358774155393"/>
</node>
</node>
</node>
</node>
</node>
<node TEXT="defaults" ID="ID_341738720" CREATED="1358773127405" MODIFIED="1358773130012">
<node TEXT="$y" ID="ID_964290644" CREATED="1358771686447" MODIFIED="1358772033831">
<node TEXT="=" ID="ID_1116792020" CREATED="1358771694145" MODIFIED="1358773753364">
<node TEXT="&apos;C&apos;" ID="ID_783762450" CREATED="1358772090433" MODIFIED="1358774155409"/>
</node>
<node TEXT="if" ID="ID_613979740" CREATED="1358772543811" MODIFIED="1358772547790">
<node TEXT="$x != &apos;b&apos;" ID="ID_870702076" CREATED="1358772548717" MODIFIED="1358774155409">
<node TEXT="=" ID="ID_787251372" CREATED="1358771694145" MODIFIED="1358773081800">
<node TEXT="&apos;D&apos;, &apos;E&apos;" ID="ID_1237281810" CREATED="1358771696930" MODIFIED="1358774155409"/>
</node>
</node>
</node>
</node>
</node>
</node>
</node>
<node TEXT="&apos;chained defaults&apos;" ID="ID_1467950826" CREATED="1358773225471" MODIFIED="1358773232304">
<node TEXT="$x" ID="ID_274452092" CREATED="1358771686447" MODIFIED="1358771690739">
<node TEXT=":" ID="ID_1799557580" CREATED="1358771694145" MODIFIED="1358771696269">
<node TEXT="&apos;a&apos;, &apos;b&apos;, &apos;c&apos;" ID="ID_1862104675" CREATED="1358771696930" MODIFIED="1358774155409"/>
<node TEXT="&apos;d&apos;" ID="ID_1425054760" CREATED="1358772079737" MODIFIED="1358774155424">
<node TEXT="$y" ID="ID_1197114438" CREATED="1358773039601" MODIFIED="1358773044126">
<node TEXT=":" ID="ID_1013589851" CREATED="1358773044632" MODIFIED="1358773045865">
<node TEXT="&apos;A&apos;, &apos;B&apos;" ID="ID_1630482025" CREATED="1358773046231" MODIFIED="1358774155424"/>
</node>
</node>
</node>
</node>
<node TEXT="defaults" ID="ID_932703816" CREATED="1358773127405" MODIFIED="1358773130012">
<node TEXT="$y" ID="ID_14489922" CREATED="1358771686447" MODIFIED="1358773306171">
<node TEXT="=" ID="ID_661997597" CREATED="1358771694145" MODIFIED="1358773287357">
<node TEXT="$z" ID="ID_1592316371" CREATED="1358772090433" MODIFIED="1358773309603"/>
</node>
<node TEXT="if" ID="ID_1127405516" CREATED="1358772543811" MODIFIED="1358772547790">
<node TEXT="$z != &apos;b&apos;" ID="ID_177630975" CREATED="1358772548717" MODIFIED="1358774155424">
<node TEXT="=" ID="ID_538757568" CREATED="1358771694145" MODIFIED="1358773081800">
<node TEXT="&apos;D&apos;, &apos;E&apos;" ID="ID_1845704476" CREATED="1358771696930" MODIFIED="1358774155424"/>
</node>
</node>
</node>
</node>
<node TEXT="$z" ID="ID_887686881" CREATED="1358773280890" MODIFIED="1358773321646">
<node TEXT="=" ID="ID_1851788292" CREATED="1358773284049" MODIFIED="1358773285438">
<node TEXT="$x" ID="ID_774804165" CREATED="1358773290234" MODIFIED="1358773292232"/>
</node>
</node>
</node>
</node>
</node>
<node TEXT="&apos;skip&apos;" ID="ID_430461928" CREATED="1358774092033" MODIFIED="1358774096027">
<node TEXT="$x" ID="ID_1333571883" CREATED="1358771686447" MODIFIED="1358771690739">
<node TEXT=":" ID="ID_344152939" CREATED="1358771694145" MODIFIED="1358771696269">
<node TEXT="&apos;a&apos;, &apos;b&apos;, &apos;c&apos;" ID="ID_245500581" CREATED="1358771696930" MODIFIED="1358774155440">
<node TEXT="$y" ID="ID_1874747914" CREATED="1358771686447" MODIFIED="1358772033831">
<node TEXT=":" ID="ID_563313066" CREATED="1358771694145" MODIFIED="1358771696269">
<node TEXT="&apos;A&apos;, &apos;C&apos;" ID="ID_858592683" CREATED="1358771696930" MODIFIED="1358774280786"/>
</node>
<node TEXT="if" ID="ID_139966505" CREATED="1358774115113" MODIFIED="1358774120714">
<node TEXT="$x == &apos;b&apos;" ID="ID_1945569078" CREATED="1358774121205" MODIFIED="1358774155440">
<node TEXT="skip" ID="ID_1438527649" CREATED="1358774131275" MODIFIED="1358774133896"/>
</node>
</node>
</node>
</node>
</node>
</node>
</node>
<node TEXT="&apos;rule stack&apos;" ID="ID_574734306" CREATED="1358774467663" MODIFIED="1358774474357">
<node TEXT="$x" ID="ID_1968583002" CREATED="1358778250023" MODIFIED="1358778253098">
<node TEXT=":" ID="ID_1590011441" CREATED="1358778253697" MODIFIED="1358778255211">
<node TEXT="&quot;a&quot;" ID="ID_1442661720" CREATED="1358778256185" MODIFIED="1358778260835"/>
</node>
</node>
<node TEXT="$x" ID="ID_339296102" CREATED="1358778250023" MODIFIED="1358778253098">
<node TEXT=":" ID="ID_1494051383" CREATED="1358778253697" MODIFIED="1358778255211">
<node TEXT="&quot;b&quot;" ID="ID_711532735" CREATED="1358778256185" MODIFIED="1358778277028"/>
</node>
</node>
<node TEXT="$x" ID="ID_118336380" CREATED="1358778250023" MODIFIED="1358778253098">
<node TEXT=":" ID="ID_225108249" CREATED="1358778253697" MODIFIED="1358778255211">
<node TEXT="&quot;c&quot;" ID="ID_1601045260" CREATED="1358778256185" MODIFIED="1358778283135"/>
</node>
</node>
</node>
</node>
</node>
</node>
</node>
</node>
</node>
</map>
