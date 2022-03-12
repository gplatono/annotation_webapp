/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package beans;

import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

/**
 * This is a bean class used to store all the scene-related info
 * @author gplatono
 */
@Entity
public class Scene implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
    
    private String path;
    private String name;
    private String taskType;

    /**
     *
     */
    public Scene() {
    }

    /**
     *
     * @param id
     * @param path
     */
    public Scene(int id, String path) {
        this.id = id;
        this.path = path;
    }    

    /**
     *
     * @param path
     * @param name
     * @param taskType
     */
    public Scene(String path, String name, String taskType) {
        this.id = id;
        this.path = path;
        this.name = name;
        this.taskType = taskType;
    }   

    /**
     *
     * @param id
     * @param path
     * @param name
     * @param taskType
     */
    public Scene(int id, String path, String name, String taskType) {
        this.id = id;
        this.path = path;
        this.name = name;
        this.taskType = taskType;
    }
    
    /**
     *
     * @return
     */
    public int getId() {
        return id;
    }

    /**
     *
     * @param id
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     *
     * @return
     */
    public String getPath() {
        return path;
    }

    /**
     *
     * @param path
     */
    public void setPath(String path) {
        this.path = path;
    }

    /**
     *
     * @return
     */
    public String getName() {
        return name;
    }

    /**
     *
     * @param name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     *
     * @return
     */
    public String getTaskType() {
        return taskType;
    }

    /**
     *
     * @param taskType
     */
    public void setTaskType(String taskType) {
        this.taskType = taskType;
    }
    
    /**
     *
     * @param object
     * @return
     */
    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Scene)) {
            return false;
        }
        Scene other = (Scene) object;
        if (this.id != other.id) {
            return false;
        }
        return true;
    }

    /**
     *
     * @return
     */
    @Override
    public String toString() {
        return "beans.Scene[ id=" + id + " ]";
    }
    
}
