package org.glowa.danube.deepactors.resmgt;

import java.io.File;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.glowa.danube.deepactors.util.DeepActorLogger;

/**
 * ToDo: javadoc.
 * 
 * @invariant {@code Todo:name} - ToDo:body.
 * 
 * @author janisch
 * @version $Id: ResourceImpl.java,v 1.7 2007/11/28 10:27:38 janisch Exp $ 
 */
public class ResourceImpl implements ResourceAdmin, ResourceAllocator{

    private final DeepActorLogger log =
        DeepActorLogger.newInstance(ResourceImpl.class);

    private Map<String, Set<Class<?>>> classMap = new HashMap<String, Set<Class<?>>>();
    private Map<String, Set<File>> fileMap = new HashMap<String, Set<File>>();
    private Map<String, Boolean> cfgBoolMap = new HashMap<String, Boolean>();
    
    private ClassLoader classLoader;
    
    public ResourceImpl(ClassLoader cl) {
        classLoader = cl;
    }
    
    public void addClassSet(String key, Set<String> classNames) {
        if(classMap.containsKey(key))
            log.warn("Replacing resource with key "+key+" ...");
        Set<Class<?>> classesToPut = new HashSet<Class<?>>();
        for(String className:classNames) {
            Class<?> c = createAndCheckClass(className);
            if(c != null) classesToPut.add(c);
            else log.warn("Ignoring resource "+className+" with key "+key);
        }
        classMap.put(key, classesToPut);
        log.debug("Added " + classesToPut.size() + " classes with key "+key);
    }
    private Class<?> createAndCheckClass(String className) {
        Class<?> c = null;
        try {
            if(classLoader == null){
              c = Class.forName(className);
            }
            else{
              c = Class.forName(className, true, classLoader);
            }
            Object testObj = c.newInstance();
        }
        catch (InstantiationException e) { log.exception(e); } 
        catch (IllegalAccessException e) { log.exception(e); }   
        catch (ClassNotFoundException e) { log.exception(e); }
        return c;
    }

    public void addClass(String key, String className) {
        Set<String> classNames = new HashSet<String>();
        classNames.add(className);
        addClassSet(key,classNames);
    }
    
    public void addFileSet(String key, Set<String> fileNames) {
        if(fileMap.containsKey(key))
            log.warn("Replacing resource with key "+key+" ...");
        Set<File> filesToPut = new HashSet<File>();
        for(String fileName:fileNames) {
            File f = new File(fileName);
            if(f.canRead()) filesToPut.add(f);
            else log.warn(fileName + " not readable. Ignoring file resource.");    
        }
        if(!filesToPut.isEmpty()) fileMap.put(key, filesToPut);
        else log.warn("Resource with key "+key
                +" contained no valid file names.");
    }
    
    public void addFile(String key, String fileName) {
        Set<String> fileNames = new HashSet<String>();
        fileNames.add(fileName);
        addFileSet(key,fileNames);
    }
    
    public void addConfigBoolean(String key, boolean value) {
        if(cfgBoolMap.containsKey(key)){
            log.warn("Replacing value of resource entry <" 
                    + key + "," + cfgBoolMap.remove(key).toString()  
                    + "> with value "+ value +" ...");
        }
        cfgBoolMap.put(key, value);
    }
    
    
    // -- Queries ----------------------------------------------------------
    
    public Set<Class<?>> getClassSet(String key) {
        return classMap.get(key); 
    }
    
    public Set<File> getFileSet(String key) { 
        return fileMap.get(key); 
    }
    
    @SuppressWarnings("unchecked")
    public Class getClass(String key) { 
        try { return classMap.get(key).iterator().next();}
        catch(NullPointerException e) { return null; }
    }
    public File getFile(String key) { 
        try { return fileMap.get(key).iterator().next();}
        catch(NullPointerException e) { return null; }
    }
    public boolean getConfigBoolean(String key){
        return cfgBoolMap.get(key);
    }

    public boolean isClass(String key) {
        return (classMap.containsKey(key) && classMap.get(key).size() == 1); 
    }
    public boolean isFile(String key) {
        return (fileMap.containsKey(key) && fileMap.get(key).size() == 1);
    }
    public boolean isClassSet(String key) {
        return (classMap.containsKey(key) && classMap.get(key).size() != 1); 
    }
    public boolean isFileSet(String key) {
        return (fileMap.containsKey(key) && fileMap.get(key).size() != 1);
    }
    public boolean isConfigBoolean(String key){
        return cfgBoolMap.containsKey(key);
    }
    public ClassLoader getClassLoader() {
        return classLoader;
    }
}

/**
 * $Log: ResourceImpl.java,v $
 * Revision 1.7  2007/11/28 10:27:38  janisch
 * - Update to Danubia 2.1.1
 * - Bugfix AbstractActorModel: query amd not via proxeltable but from am
 * - Align and fix unit tests
 * - Eclipse compiler settings: ignore raw type warnings
 * - Resolved any other compiler warning
 *
 * Revision 1.6  2007/10/31 10:16:50  janisch
 * Need to use dinstinguished Danubia class loader for the instantiation
 * of model, actors, plans and actions
 *
 * Revision 1.5  2007/10/29 09:31:51  janisch
 * - Refactored generics according to java 1.6 compiler warnings
 *
 * Revision 1.4  2006/08/16 13:55:33  janisch
 * Added configuration parameters of models' cfg file as
 * configuration resource.
 *
 * Revision 1.3  2005/11/22 17:27:55  janisch
 * Added debug message.
 *
 * Revision 1.2  2005/09/08 08:13:00  janisch
 * Worked on javadoc tags: changed e.g. @pre to @pre.condition etc.
 *
 * Revision 1.1  2005/08/26 11:17:18  janisch
 * Release 1.0.0
 *
 */