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

-----------------------------------------------------------------------------------
Upload
Anaty formulaire possible misy input:file
Mande any amin Action atsika (method Controller)
-> Mila tonga anaty leh action ilay file 
    - Creer class MapFile
        - Filename
        - Extension
        - Byte[]
        -> instancena aty amin Map 

    - Misy type MapFile ve ?
        si oui 
            - Instancena
        


----------------------------------------------------------------------------------------
Sprint 9
Atao disponible anaty controller lay session Utilisateur
Creer Class Mysession 
    - Map<String , Object> 
    - Method utilitaire pour recuperer ou ajouter variable
Refa mahita an iny ny framework atsika dia injectena

Rehefa vita ny invoke dia atao mis a jour ny session 

----------------------------------------------------------------------------------------
Sprint10
Tsy maintsy Authentifie lay olona 
- Tsy maintsy manana role specifique
-> 
    - Manisy Annotation
    - Verifiena anaty framework hoe authentifie sa tsy 
    - isaky mahita annotation , Verifiena hoe miexiste ve ny nom ana session
    -  Ahona no alalana hoe inona ny anaran variable fa authentifie ny nom an utilisateur sa tsia ?
        - Par convention 
        - Par configuration
            - web xml : init param > ty ny anaran variable verifiena
            - alaina ilay param (in 1 mandeh ihany ) -> apetraka anaty servlet context

--------------------------------------------------------------------------------------------
Sprint10bis
- Manampy variable ametrahan Dev ny role any 
- Ref mahit anleh annotation 
    - verifiena hoe misy anlay variable ve anaty xml . Zay nametrahana ny role
    - si oui 
        - Alaina ilay variable , Verifiena hoe misy ve anaty session 
