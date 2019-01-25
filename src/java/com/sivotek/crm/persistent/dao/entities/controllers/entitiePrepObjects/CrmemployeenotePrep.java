/*
 * ENTERPRISE RESOURCE PLANNING AND CUSTOMER RELATIONSHIP MANAGEMENT SYSTEM.
 * DEVELOPED BY OKOYE VICTOR FOR SIVOTEK SOLUTIONS LLC.
 * ALL RIGHT RESERVED 2014
 */


package com.sivotek.crm.persistent.dao.entities.controllers.entitiePrepObjects;

import com.sivotek.crm.persistent.dao.entities.Company;
import com.sivotek.crm.persistent.dao.entities.CompanyPK;
import com.sivotek.crm.persistent.dao.entities.Companyemployee;
import com.sivotek.crm.persistent.dao.entities.CompanyemployeePK;
import com.sivotek.crm.persistent.dao.entities.Crmemployeenote;
import com.sivotek.crm.persistent.dao.entities.CrmemployeenotePK;
import com.sivotek.crm.persistent.dao.entities.controllers.CompanyJpaController;
import com.sivotek.crm.persistent.dao.entities.controllers.CompanyemployeeJpaController;
import com.sivotek.crm.persistent.dao.entities.controllers.CrmemployeenoteJpaController;
import com.sivotek.crm.xsd.jaxb.response.Response;
import java.util.Collection;
//import java.util.Collection;
import java.util.Date;
import java.util.List;
import javax.xml.bind.JAXBElement;

/**
 *
 * @author okoyevictor
 */
public class CrmemployeenotePrep {
   private Response response;
   private String status = "";
   private String statusmessage = "";
   private int id = 0;
   
   //getters and setters
   public int getId() 
   {return id;}
   public void setId(int id) 
   {this.id = id;}
   
   public String getStatus() 
   {return status;}
   public void setStatus(String status) 
   {this.status = status;}
 
   public String getStatusmessage() 
   {return statusmessage;}
   public void setStatusmessage(String statusmessage) 
   {this.statusmessage = statusmessage;}
   
     private String getElementStringValueFromList(String elementName, List elementList) {
       for (Object elementList1 : elementList) {
           JAXBElement e = (JAXBElement) elementList1;
           if (e.getName().getLocalPart().equals(elementName)) {
               return e.getValue().toString();
           }
       }
        return null;
    }
     
   public List<Response.Page.Elements.Element> Crmemployeenotes(List children, int publickey, int companyID)
   {
    //create response Object Factory
    com.sivotek.crm.xsd.jaxb.response.ObjectFactory responseOF = new com.sivotek.crm.xsd.jaxb.response.ObjectFactory();
    //create response <Page> Object
    com.sivotek.crm.xsd.jaxb.response.Response.Page responsePage = responseOF.createResponsePage();
    //create response <elements> object
    com.sivotek.crm.xsd.jaxb.response.Response.Page.Elements responseElements = responseOF.createResponsePageElements();
    //initialize response object
    response = responseOF.createResponse();
    Response.Page.Elements.Element resElement = responseOF.createResponsePageElementsElement();
    List<Response.Page.Elements.Element> responseElementList = responseElements.getElement();
     
    Company company = new Company();
    CompanyPK companyPK = new CompanyPK();
    companyPK.setCompanyid(companyID);
    companyPK.setPubkey(publickey);
    CompanyJpaController companyJpaController = new CompanyJpaController();
    try
    {
        company = companyJpaController.findCompany(companyPK);
        if(company.getCompanyPK().getCompanyid() > 0)
        {
            String employeeid = getElementStringValueFromList("employeeid", children);
            String notespool = getElementStringValueFromList("notespool", children);
            Companyemployee companyemployee = new Companyemployee();
            CompanyemployeeJpaController companyemployeeJpaController = new CompanyemployeeJpaController();
            CompanyemployeePK companyemployeePK = new CompanyemployeePK();
            companyemployeePK.setPubkey(publickey);
            companyemployeePK.setId(Integer.parseInt(employeeid));
            companyemployee = companyemployeeJpaController.findCompanyemployee(companyemployeePK);
            
           if(companyemployee.getCompanyemployeePK().getId() > 0)
           {
               if(companyemployee.getCrmemployeenoteCollection().size() > 0 && !notespool.equalsIgnoreCase(""))
               {
                   Collection<Crmemployeenote> crmemployeenoteColl = companyemployee.getCrmemployeenoteCollection();
                   Crmemployeenote crmemployeenote = new Crmemployeenote();
                   String pools = "";
                   
                   for(Crmemployeenote note : crmemployeenoteColl)
                   {
                        crmemployeenote = note;
                        pools = crmemployeenote.getNotespool();
                        pools +="<br/>";
                        pools += notespool;
                       
                   }
                    crmemployeenote.setChangeddate(new Date());
                    crmemployeenote.setChangedfrom("com.sivotek.crm.persistent.dao.entities.controllers.entitiePrepObjects.Crmemployeenote.class");
                    crmemployeenote.setNotespool(notespool);
                    crmemployeenote.setChangedby(companyemployee.getCompanyemployeePK().getId());
                    CrmemployeenoteJpaController crmemployeenoteJpaController = new CrmemployeenoteJpaController();
                    crmemployeenoteJpaController.edit(crmemployeenote);
                    //////////////////
                    resElement = responseOF.createResponsePageElementsElement();
                    resElement.setId(""+crmemployeenote.getCrmemployeenotePK().getId());
                    resElement.setElementstatus("OK");
                    resElement.setElementstatusmessage("Success");
                    ////
                    responseElementList.add(resElement);
                  
               }
               else if(companyemployee.getCrmemployeenoteCollection().size() > 0 && notespool.equalsIgnoreCase(""))
               {
                   Collection<Crmemployeenote> crmemployeenoteColl = companyemployee.getCrmemployeenoteCollection();
                   for(Crmemployeenote note : crmemployeenoteColl)
                   {
                        //////////////////
                        resElement = responseOF.createResponsePageElementsElement();
                        resElement.setId("crmemployeenote");
                        resElement.setNotespool(note.getNotespool());
                        ////
                        responseElementList.add(resElement);
                   }
                   
               }
               else
               {
                long bint = System.currentTimeMillis();
                String p = ""+bint;
                Crmemployeenote crmemployeenote = new Crmemployeenote();
                CrmemployeenotePK crmemployeenotePK = new CrmemployeenotePK();
                crmemployeenotePK.setPubkey(publickey);
                crmemployeenotePK.setId(Integer.parseInt(p.substring(7)));
                crmemployeenote.setCrmemployeenotePK(crmemployeenotePK);
                
                crmemployeenote.setCompanyemployee(companyemployee);
                crmemployeenote.setCreateddate(new Date());
                crmemployeenote.setCreatedby(companyemployee.getCompanyemployeePK().getId());
                crmemployeenote.setCreatedfrom("com.sivotek.crm.persistent.dao.entities.controllers.entitiePrepObjects.Crmemployeenote.class");
                
                crmemployeenote.setNotespool(notespool);
                CrmemployeenoteJpaController crmemployeenoteJpaController = new CrmemployeenoteJpaController();
                crmemployeenoteJpaController.create(crmemployeenote);
                 //////////////////
                 resElement = responseOF.createResponsePageElementsElement();
                 resElement.setId(""+crmemployeenote.getCrmemployeenotePK().getId());
                 resElement.setElementstatus("OK");
                 resElement.setElementstatusmessage("Success");
                 ////
                 responseElementList.add(resElement);
               }
               
           }
           else
           {
                   //////////////////
                   resElement = responseOF.createResponsePageElementsElement();
                   resElement.setId("crmemployeenote");
                   resElement.setElementstatus("FAIL");
                   resElement.setElementstatusmessage("Invalid Employee ID.");
                   ////
                   responseElementList.add(resElement);
           }
        }
        else
        {
                   //////////////////
                   resElement = responseOF.createResponsePageElementsElement();
                   resElement.setId("crmemployeenote");
                   resElement.setElementstatus("FAIL");
                   resElement.setElementstatusmessage("Invalid Company Detail.");
                   ////
                   responseElementList.add(resElement);
        }
    }catch(Exception ex){}
       
       return responseElementList;
       
   }

}
