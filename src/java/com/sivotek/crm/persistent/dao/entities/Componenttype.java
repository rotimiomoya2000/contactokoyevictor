/*
 * ENTERPRISE RESOURCE PLANNING AND CUSTOMER RELATIONSHIP MANAGEMENT SYSTEM.
 * DEVELOPED BY OKOYE VICTOR FOR SIVOTEK SOLUTIONS LLC.
 * ALL RIGHT RESERVED 2014
 */


package com.sivotek.crm.persistent.dao.entities;

import java.io.Serializable;
import java.util.Collection;
import java.util.Date;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.JoinColumns;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author okoyevictor
 */
@Entity
@Table(catalog = "sivotekcrm_", schema = "")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Componenttype.findAll", query = "SELECT c FROM Componenttype c"),
    @NamedQuery(name = "Componenttype.findById", query = "SELECT c FROM Componenttype c WHERE c.componenttypePK.id = :id"),
    @NamedQuery(name = "Componenttype.findByPubkey", query = "SELECT c FROM Componenttype c WHERE c.componenttypePK.pubkey = :pubkey"),
    @NamedQuery(name = "Componenttype.findByComponenttype", query = "SELECT c FROM Componenttype c WHERE c.componenttype = :componenttype"),
    @NamedQuery(name = "Componenttype.findByCreateddate", query = "SELECT c FROM Componenttype c WHERE c.createddate = :createddate"),
    @NamedQuery(name = "Componenttype.findByCreatedfrom", query = "SELECT c FROM Componenttype c WHERE c.createdfrom = :createdfrom"),
    @NamedQuery(name = "Componenttype.findByChangeddate", query = "SELECT c FROM Componenttype c WHERE c.changeddate = :changeddate"),
    @NamedQuery(name = "Componenttype.findByChangedfrom", query = "SELECT c FROM Componenttype c WHERE c.changedfrom = :changedfrom")})
public class Componenttype implements Serializable {
    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected ComponenttypePK componenttypePK;
    @Size(max = 255)
    @Column(length = 255)
    private String componenttype;
    @Lob
    @Size(max = 65535)
    @Column(length = 65535)
    private String description;
    @Temporal(TemporalType.TIMESTAMP)
    private Date createddate;
    @Size(max = 150)
    @Column(length = 150)
    private String createdfrom;
    @Temporal(TemporalType.TIMESTAMP)
    private Date changeddate;
    @Size(max = 150)
    @Column(length = 150)
    private String changedfrom;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "componenttype")
    private Collection<Productcomponents> productcomponentsCollection;
    @JoinColumns({
        @JoinColumn(name = "CHANGEDBY", referencedColumnName = "ID"),
        @JoinColumn(name = "pubkey", referencedColumnName = "pubkey", nullable = false, insertable = false, updatable = false)})
    @ManyToOne(optional = false)
    private Companyemployee companyemployee;
    @JoinColumns({
        @JoinColumn(name = "CREATEDBY", referencedColumnName = "ID"),
        @JoinColumn(name = "pubkey", referencedColumnName = "pubkey", nullable = false, insertable = false, updatable = false)})
    @ManyToOne(optional = false)
    private Companyemployee companyemployee1;
    @JoinColumns({
        @JoinColumn(name = "COMPANYID", referencedColumnName = "COMPANYID"),
        @JoinColumn(name = "pubkey", referencedColumnName = "pubkey", nullable = false, insertable = false, updatable = false)})
    @ManyToOne(optional = false)
    private Company company;

    public Componenttype() {
    }

    public Componenttype(ComponenttypePK componenttypePK) {
        this.componenttypePK = componenttypePK;
    }

    public Componenttype(int id, int pubkey) {
        this.componenttypePK = new ComponenttypePK(id, pubkey);
    }

    public ComponenttypePK getComponenttypePK() {
        return componenttypePK;
    }

    public void setComponenttypePK(ComponenttypePK componenttypePK) {
        this.componenttypePK = componenttypePK;
    }

    public String getComponenttype() {
        return componenttype;
    }

    public void setComponenttype(String componenttype) {
        this.componenttype = componenttype;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Date getCreateddate() {
        return createddate;
    }

    public void setCreateddate(Date createddate) {
        this.createddate = createddate;
    }

    public String getCreatedfrom() {
        return createdfrom;
    }

    public void setCreatedfrom(String createdfrom) {
        this.createdfrom = createdfrom;
    }

    public Date getChangeddate() {
        return changeddate;
    }

    public void setChangeddate(Date changeddate) {
        this.changeddate = changeddate;
    }

    public String getChangedfrom() {
        return changedfrom;
    }

    public void setChangedfrom(String changedfrom) {
        this.changedfrom = changedfrom;
    }

    @XmlTransient
    public Collection<Productcomponents> getProductcomponentsCollection() {
        return productcomponentsCollection;
    }

    public void setProductcomponentsCollection(Collection<Productcomponents> productcomponentsCollection) {
        this.productcomponentsCollection = productcomponentsCollection;
    }

    public Companyemployee getCompanyemployee() {
        return companyemployee;
    }

    public void setCompanyemployee(Companyemployee companyemployee) {
        this.companyemployee = companyemployee;
    }

    public Companyemployee getCompanyemployee1() {
        return companyemployee1;
    }

    public void setCompanyemployee1(Companyemployee companyemployee1) {
        this.companyemployee1 = companyemployee1;
    }

    public Company getCompany() {
        return company;
    }

    public void setCompany(Company company) {
        this.company = company;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (componenttypePK != null ? componenttypePK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Componenttype)) {
            return false;
        }
        Componenttype other = (Componenttype) object;
        if ((this.componenttypePK == null && other.componenttypePK != null) || (this.componenttypePK != null && !this.componenttypePK.equals(other.componenttypePK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.sivotek.crm.persistent.dao.entities.Componenttype[ componenttypePK=" + componenttypePK + " ]";
    }

}
