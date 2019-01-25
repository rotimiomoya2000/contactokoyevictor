/*
 * ENTERPRISE RESOURCE PLANNING AND CUSTOMER RELATIONSHIP MANAGEMENT SYSTEM.
 * DEVELOPED BY OKOYE VICTOR FOR SIVOTEK SOLUTIONS LLC.
 * ALL RIGHT RESERVED 2014
 */


package com.sivotek.crm.persistent.dao.entities.controllers.entitiePrepObjects;

import com.sivotek.crm.persistent.dao.entities.Company;
import com.sivotek.crm.persistent.dao.entities.CompanyPK;
import com.sivotek.crm.persistent.dao.entities.Companybank;
import com.sivotek.crm.persistent.dao.entities.CompanybankPK;
import com.sivotek.crm.persistent.dao.entities.Companyemployee;
import com.sivotek.crm.persistent.dao.entities.controllers.CompanyJpaController;
import com.sivotek.crm.persistent.dao.entities.controllers.CompanybankJpaController;
import com.sivotek.crm.xsd.jaxb.response.Response;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import javax.xml.bind.JAXBElement;

/**
 *
 * @author okoyevictor
 */
public class CompanybankPrep {
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
     
    public List<Response.Page.Elements.Element> companybank(List children, int publickey, int companyID)
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
        
        try{
            company = companyJpaController.findCompany(companyPK);
            if(company.getCompanyPK().getCompanyid() > 0)
            {
                String employeeid = getElementStringValueFromList("employeeid", children);
                String bankname = getElementStringValueFromList("bankname", children);
                String bankaccount = getElementStringValueFromList("bankaccount", children);
                String bankIBAN = getElementStringValueFromList("bankIBAN", children);
                String bankcountry = getElementStringValueFromList("bankcountry", children);
                String isDefault = getElementStringValueFromList("isDefault", children);
                
                Companyemployee companyemployee = new Companyemployee();
                
                Companybank companybank = new Companybank();
                CompanybankPK companybankPK = new CompanybankPK();
                companybankPK.setPubkey(publickey);
                long bint = System.currentTimeMillis();
                String p = ""+bint;
                companybankPK.setBankid(Integer.parseInt(p.substring(7)));
                companybank.setCompanybankPK(companybankPK);
                
                //find company employee from company employee collection
                if(company.getCompanyemployeeCollection().size() > 0)
                {
                    Collection<Companyemployee> employeeColl = company.getCompanyemployeeCollection();
                    for(Companyemployee employee : employeeColl)
                    {
                        if(employee.getCompanyemployeePK().getId() == Integer.parseInt(employeeid))
                        {
                            companyemployee = employee;
                            break;
                        }
                    }
                }//
                
                //if company employee was found...
               if(companyemployee.getCompanyemployeePK().getId() > 0)
                {
                 if(bankname != null && !bankname.isEmpty() && bankaccount != null && !bankaccount.isEmpty())
                  {
                    CompanybankJpaController companybankJpaController = new CompanybankJpaController();
                    companybank.setCompany(company);
                    companybank.setCompanyemployee(companyemployee);
                    companybank.setBankname(bankname);
                    companybank.setBankaccount(bankaccount);
                    companybank.setBankiban(bankIBAN);
                    companybank.setBankcountry(bankcountry);
                    if(Boolean.parseBoolean(isDefault)==true)
                    {
                        if(company.getCompanybankCollection().size() > 0)
                        {
                            Collection<Companybank> bankColl = company.getCompanybankCollection();
                            for(Companybank compbank : bankColl)
                            {
                                if(compbank.getIsDefault() == true)
                                {
                                    compbank.setIsDefault(Boolean.FALSE);
                                    compbank.setChangeddate(new Date());
                                    compbank.setChangedfrom("com.sivotek.crm.persistent.dao.entities.controllers.entitiePrepObjects.CompanybankPrep.class");
                                    companybankJpaController.edit(compbank);
                                }
                            }
                        }
                    }
                    
                        companybank.setIsDefault(Boolean.parseBoolean(isDefault));
                        companybank.setCreateddate(new Date());
                        companybank.setCreatedfrom("com.sivotek.crm.persistent.dao.entities.controllers.entitiePrepObjects.CompanybankPrep.class");
                        companybankJpaController.create(companybank);
                        
                        //////////////////
                        resElement = responseOF.createResponsePageElementsElement();
                        resElement.setId("companybank");
                        resElement.setBankid(companybank.getCompanybankPK().getBankid());
                        resElement.setElementstatus("OK");
                        resElement.setElementstatusmessage("Success");
                        ////
                        responseElementList.add(resElement);
                 }
                 
                 //load all company bankaccounts
                 else if(bankname != null && bankname.isEmpty() && bankaccount != null && bankaccount.isEmpty())
                 {
                     if(company.getCompanybankCollection().size() > 0)
                     {
                         Collection<Companybank> companybankColl = company.getCompanybankCollection();
                         for(Companybank bank : companybankColl)
                         {
                            //////////////////
                            resElement = responseOF.createResponsePageElementsElement();
                            resElement.setId("companybank");
                            resElement.setBankid(bank.getCompanybankPK().getBankid());
                            resElement.setBankname(bank.getBankname());
                            resElement.setBankaccount(bank.getBankaccount());
                            resElement.setBankcountry(bank.getBankcountry());
                            resElement.setBankIBAN(bank.getBankiban());
                            resElement.setIsBillable(bank.getIsDefault());
                            ////
                            responseElementList.add(resElement);
                         }
                     }//
                     else
                     {
                            //////////////////
                            resElement = responseOF.createResponsePageElementsElement();
                            resElement.setId("companybank");
                            resElement.setElementstatus("FAIL");
                            resElement.setElementstatusmessage("no bank records");
                            ////
                            responseElementList.add(resElement);
                     }
                 }
                 
                }//
               
               else{
                        //////////////////
                        resElement = responseOF.createResponsePageElementsElement();
                        resElement.setId("companybank");
                        resElement.setElementstatus("FAIL");
                        resElement.setElementstatusmessage("Invalid Employee ID");
                        ////
                        responseElementList.add(resElement);
                     } 
            } 
            else{
                    //////////////////
                    resElement = responseOF.createResponsePageElementsElement();
                    resElement.setId("companybank");
                    resElement.setElementstatus("FAIL");
                    resElement.setElementstatusmessage("Invalid Company details");
                    ////
                    responseElementList.add(resElement);
            }
        }catch(Exception ex)
        {
                    //////////////////
                    resElement = responseOF.createResponsePageElementsElement();
                    resElement.setId("companybank");
                    resElement.setElementstatus("ERROR");
                    resElement.setElementstatusmessage(ex.getMessage());
                    ////
                    responseElementList.add(resElement);
        }
        
        
        return responseElementList;
    } 
}
