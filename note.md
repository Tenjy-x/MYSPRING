- Creer frontControllerServlet
    - Doget 
    - Dopost
        processrequest
            - output URL
    * request dia mandalo amin' ny frontControllerServlet daholy
    * Alefa anaty Path anleh test leh .jar
    * Misy web.xml anaty test
        Declarena anatiny leh servlet
            - Configurer url param
                - atao "/" amzay mandalo ao daholy 
TEST
    - Taper URL exemple ("hahah")
        - Mipoitra anleh ("hahah) eo amin Navigateur
Creer annotation
 - class
 - Methode
 - Attribut
 variable azo apetraka anaty annotation
    nom , atttribut , Aiza no possible
    Cree class
-------------------------------------------------------------------------------------
Sprint
- Creer Controller dans Myspring (mg.itu...)

- Bout de code soit
-> executer au demarrage l'application (Utiliser Listener)
-> Premier appel du frontServlet (appel method init)
    - Mila fantatra ny Controller Rehetra
    - asina attribut (List) chaine de charactere
        - Parcourir ny class rehetra , Test Misy annotation ve sa tsy misy , 
        - Raha misy de ajoutena anaty list
        - Afficher List
    Omena list package

    asina anarana variable anate web xml
        valeur package misy anleh application de test
    
-> Creer class utilitaire anaty framework
    method 
    - omena ny annotation verifiena
    - Package
    - 
Test : creation TestController
    - annotena amin Controller

Annotation method fa mila variable
Mandray (/emp/list)
/emp/list dia ao amin empController methode list
Misy variable miova ao anaty front Servlet
UrlMapping

- UrlMethod (url,String method [get ou post])

- Invoke method

-----------------------------------------------------------
Rehefa demarrer lay application dia Efa misy method anatinleh listener efa excecute
Class Apart ny Listener

--------------------------------------------------------------

Sprint 5

methode d'action retourne String
parametre : Model

creation methode
    setAttribute(Map)

String Object>
miantso map.put

manampy paramatre
    - Prefixe
    - suffixe
invoken methode de recuperena ny valeur de retour 

----------------------------------------------------------
    attribut 
        map
        Url
    alaina leh url de concatenena amin suffixe sy prefixe anaty param
    dispatcherforward

--------------------------------------------------------------
Sprint 5bis
- Declarer listenerspring ( efa misy ao : demarrage instance container)
    -> web.xml no ideclarena azy  

 <context-param>
    <param-name>contextConfigLocation</param-name>
    <param-value>/WEB-INF/applicationContext.xml</param-value>
</context-param>

<!-- Déclaration du listener qui démarre le conteneur Spring -->
<listener>
    <listener-class>org.springframework.web.context.ContextLoaderListener</listener-class>
</listener>
- Comment avoir un instance d'un container  spring ?

Sprint 6
Mamerina Json 
Refa mahita zay annotation lay framework dia tsy manao request Dispatcher fa mamerina Json 

- Manampy annotation ray zay apetaka aminleh method
zay tsy mande amin vue fa json no miverina
@webapi
Rehefa tsy misy lay annotation vaovao dia mande dispatcher
- 
- Ref string deh tsy mila mamadika Json
   Fa ref hafa dia manao toJson 

-----------------------------------------------------------------------------------
Sprint 7
Rehefa mclique btn dia makao amin FrontServlet 
Io mamantatra hoe save na hafa 
Rehefa mverifier methode dia apina hoe misy parametre ve lay methode 
1ere etape
    Tsy objet 
    save(String nom , int age)
    utiliser getParameterNames

Sprint 7 bis

