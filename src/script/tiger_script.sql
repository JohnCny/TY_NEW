-- 客户基本信息维护log触发器
create or replace trigger tiger_basic_customer_info
  after update on basic_customer_information  
  for each row
declare
  -- local variables here
begin
   if :new.chinese_name != :old.chinese_name then
      insert into customer_maintenancee_log values (sys_guid(),:new.id,:new.modify_user,null,null,
      'basic_customer_information','chinese_name',null,:old.chinese_name,:new.chinese_name,null,:new.modify_user,sysdate);
   end if;
   if :new.nationality != :old.nationality then
      insert into customer_maintenancee_log values (sys_guid(),:new.id,:new.modify_user,null,null,
      'basic_customer_information','nationality',null,:old.nationality,:new.nationality,null,:new.modify_user,sysdate);
   end if;
   if :new.sex != :old.sex then
      insert into customer_maintenancee_log values (sys_guid(),:new.id,:new.modify_user,null,null,
      'basic_customer_information','sex',null,:old.sex,:new.sex,null,:new.modify_user,sysdate);
   end if;
   if :new.pinyinenglish_name != :old.pinyinenglish_name then
      insert into customer_maintenancee_log values (sys_guid(),:new.id,:new.modify_user,null,null,
      'basic_customer_information','pinyinenglish_name',null,:old.pinyinenglish_name,:new.pinyinenglish_name,null,:new.modify_user,sysdate);
   end if;
   if :new.birthday != :old.birthday then
      insert into customer_maintenancee_log values (sys_guid(),:new.id,:new.modify_user,null,null,
      'basic_customer_information','birthday',null,:old.birthday,:new.birthday,null,:new.modify_user,sysdate);
   end if;
   if :new.card_type != :old.card_type then
      insert into customer_maintenancee_log values (sys_guid(),:new.id,:new.modify_user,null,null,
      'basic_customer_information','card_type',null,:old.card_type,:new.card_type,null,:new.modify_user,sysdate);
   end if;
   if :new.card_id != :old.card_id then
      insert into customer_maintenancee_log values (sys_guid(),:new.id,:new.modify_user,null,null,
      'basic_customer_information','card_id',null,:old.card_id,:new.card_id,null,:new.modify_user,sysdate);
   end if;
   if :new.residential_address != :old.residential_address then
      insert into customer_maintenancee_log values (sys_guid(),:new.id,:new.modify_user,null,null,
      'basic_customer_information','residential_address',null,:old.residential_address,:new.residential_address,null,:new.modify_user,sysdate);
   end if;
   if :new.zip_code != :old.zip_code then
      insert into customer_maintenancee_log values (sys_guid(),:new.id,:new.modify_user,null,null,
      'basic_customer_information','zip_code',null,:old.zip_code,:new.zip_code,null,:new.modify_user,sysdate);
   end if;
   if :new.home_phone != :old.home_phone then
      insert into customer_maintenancee_log values (sys_guid(),:new.id,:new.modify_user,null,null,
      'basic_customer_information','home_phone',null,:old.home_phone,:new.home_phone,null,:new.modify_user,sysdate);
   end if;
   if :new.telephone != :old.telephone then
      insert into customer_maintenancee_log values (sys_guid(),:new.id,:new.modify_user,null,null,
      'basic_customer_information','telephone',null,:old.telephone,:new.telephone,null,:new.modify_user,sysdate);
   end if;
   if :new.mail != :old.mail then
      insert into customer_maintenancee_log values (sys_guid(),:new.id,:new.modify_user,null,null,
      'basic_customer_information','mail',null,:old.mail,:new.mail,null,:new.modify_user,sysdate);
   end if;
   if :new.residential_propertie != :old.residential_propertie then
      insert into customer_maintenancee_log values (sys_guid(),:new.id,:new.modify_user,null,null,
      'basic_customer_information','residential_propertie',null,:old.residential_propertie,:new.residential_propertie,null,:new.modify_user,sysdate);
   end if;
   if :new.marital_status != :old.marital_status then
      insert into customer_maintenancee_log values (sys_guid(),:new.id,:new.modify_user,null,null,
      'basic_customer_information','marital_status',null,:old.marital_status,:new.marital_status,null,:new.modify_user,sysdate);
   end if;
   if :new.degree_education != :old.degree_education then
      insert into customer_maintenancee_log values (sys_guid(),:new.id,:new.modify_user,null,null,
      'basic_customer_information','degree_education',null,:old.degree_education,:new.degree_education,null,:new.modify_user,sysdate);
   end if;
   if :new.household_address != :old.household_address then
      insert into customer_maintenancee_log values (sys_guid(),:new.id,:new.modify_user,null,null,
      'basic_customer_information','household_address',null,:old.household_address,:new.household_address,null,:new.modify_user,sysdate);
   end if;
   if :new.zip_code_address != :old.zip_code_address then
      insert into customer_maintenancee_log values (sys_guid(),:new.id,:new.modify_user,null,null,
      'basic_customer_information','zip_code_address',null,:old.zip_code_address,:new.zip_code_address,null,:new.modify_user,sysdate);
   end if;
   if :new.apply_credit != :old.apply_credit then
      insert into customer_maintenancee_log values (sys_guid(),:new.id,:new.modify_user,null,null,
      'basic_customer_information','apply_credit',null,:old.apply_credit,:new.apply_credit,null,:new.modify_user,sysdate);
   end if;
   if :new.final_approval != :old.final_approval then
      insert into customer_maintenancee_log values (sys_guid(),:new.id,:new.modify_user,null,null,
      'basic_customer_information','final_approval',null,:old.final_approval,:new.final_approval,null,:new.modify_user,sysdate);
   end if;
   if :new.actual_amount != :old.actual_amount then
      insert into customer_maintenancee_log values (sys_guid(),:new.id,:new.modify_user,null,null,
      'basic_customer_information','actual_amount',null,:old.actual_amount,:new.actual_amount,null,:new.modify_user,sysdate);
   end if;
   if :new.temporary_quota != :old.temporary_quota then
      insert into customer_maintenancee_log values (sys_guid(),:new.id,:new.modify_user,null,null,
      'basic_customer_information','temporary_quota',null,:old.temporary_quota,:new.temporary_quota,null,:new.modify_user,sysdate);
   end if;
   if :new.cash_advance_ratio != :old.cash_advance_ratio then
      insert into customer_maintenancee_log values (sys_guid(),:new.id,:new.modify_user,null,null,
      'basic_customer_information','cash_advance_ratio',null,:old.cash_advance_ratio,:new.cash_advance_ratio,null,:new.modify_user,sysdate);
   end if;
   if :new.card_status != :old.card_status then
      insert into customer_maintenancee_log values (sys_guid(),:new.id,:new.modify_user,null,null,
      'basic_customer_information','card_status',null,:old.card_status,:new.card_status,null,:new.modify_user,sysdate);
   end if;
   if :new.user_id != :old.user_id then
      insert into customer_maintenancee_log values (sys_guid(),:new.id,:new.modify_user,null,null,
      'basic_customer_information','user_id',null,:old.user_id,:new.user_id,null,:new.modify_user,sysdate);
   end if;
   
exception
   WHEN OTHERS THEN
        DBMS_OUTPUT.PUT_LINE(SQLCODE||'---'||SQLERRM);

end tiger_basic_customer_info;


-- 四维授信模型维护log触发器
create or replace trigger tiger_dimensional_model_credit
  after update on dimensional_model_credit  
  for each row
declare
  -- local variables here
begin
   if :new.annual_net_income != :old.annual_net_income then
      insert into customer_maintenancee_log values (sys_guid(),:new.customer_id,:new.modify_user,null,null,
      'dimensional_model_credit','annual_net_income',null,:old.annual_net_income,:new.annual_net_income,null,:new.modify_user,sysdate);
   end if;
   if :new.account_manager_series != :old.account_manager_series then
      insert into customer_maintenancee_log values (sys_guid(),:new.customer_id,:new.modify_user,null,null,
      'dimensional_model_credit','account_manager_series',null,:old.account_manager_series,:new.account_manager_series,null,:new.modify_user,sysdate);
   end if;
   if :new.capacity_t != :old.capacity_t then
      insert into customer_maintenancee_log values (sys_guid(),:new.customer_id,:new.modify_user,null,null,
      'dimensional_model_credit','capacity_t',null,:old.capacity_t,:new.capacity_t,null,:new.modify_user,sysdate);
   end if;
   if :new.capacity_final != :old.capacity_final then
      insert into customer_maintenancee_log values (sys_guid(),:new.customer_id,:new.modify_user,null,null,
      'dimensional_model_credit','capacity_final',null,:old.capacity_final,:new.capacity_final,null,:new.modify_user,sysdate);
   end if;
   if :new.register_value != :old.register_value then
      insert into customer_maintenancee_log values (sys_guid(),:new.customer_id,:new.modify_user,null,null,
      'dimensional_model_credit','register_value',null,:old.register_value,:new.register_value,null,:new.modify_user,sysdate);
   end if;
   if :new.register_weight != :old.register_weight then
      insert into customer_maintenancee_log values (sys_guid(),:new.customer_id,:new.modify_user,null,null,
      'dimensional_model_credit','register_weight',null,:old.register_weight,:new.register_weight,null,:new.modify_user,sysdate);
   end if;
   if :new.house_value != :old.house_value then
      insert into customer_maintenancee_log values (sys_guid(),:new.customer_id,:new.modify_user,null,null,
      'dimensional_model_credit','house_value',null,:old.house_value,:new.house_value,null,:new.modify_user,sysdate);
   end if;
   if :new.house_weight != :old.house_weight then
      insert into customer_maintenancee_log values (sys_guid(),:new.customer_id,:new.modify_user,null,null,
      'dimensional_model_credit','house_weight',null,:old.house_weight,:new.house_weight,null,:new.modify_user,sysdate);
   end if;
   if :new.marriage_value != :old.marriage_value then
      insert into customer_maintenancee_log values (sys_guid(),:new.customer_id,:new.modify_user,null,null,
      'dimensional_model_credit','marriage_value',null,:old.marriage_value,:new.marriage_value,null,:new.modify_user,sysdate);
   end if;
   if :new.marriage_weight != :old.marriage_weight then
      insert into customer_maintenancee_log values (sys_guid(),:new.customer_id,:new.modify_user,null,null,
      'dimensional_model_credit','marriage_weight',null,:old.marriage_weight,:new.marriage_weight,null,:new.modify_user,sysdate);
   end if;
   if :new.title_value != :old.title_value then
      insert into customer_maintenancee_log values (sys_guid(),:new.customer_id,:new.modify_user,null,null,
      'dimensional_model_credit','title_value',null,:old.title_value,:new.title_value,null,:new.modify_user,sysdate);
   end if;
   if :new.title_weight != :old.title_weight then
      insert into customer_maintenancee_log values (sys_guid(),:new.customer_id,:new.modify_user,null,null,
      'dimensional_model_credit','title_weight',null,:old.title_weight,:new.title_weight,null,:new.modify_user,sysdate);
   end if;
   if :new.living_combined_value != :old.living_combined_value then
      insert into customer_maintenancee_log values (sys_guid(),:new.customer_id,:new.modify_user,null,null,
      'dimensional_model_credit','living_combined_value',null,:old.living_combined_value,:new.living_combined_value,null,:new.modify_user,sysdate);
   end if;
   if :new.living_t != :old.living_t then
      insert into customer_maintenancee_log values (sys_guid(),:new.customer_id,:new.modify_user,null,null,
      'dimensional_model_credit','living_t',null,:old.living_t,:new.living_t,null,:new.modify_user,sysdate);
   end if;
   if :new.living_final != :old.living_final then
      insert into customer_maintenancee_log values (sys_guid(),:new.customer_id,:new.modify_user,null,null,
      'dimensional_model_credit','living_final',null,:old.living_final,:new.living_final,null,:new.modify_user,sysdate);
   end if;
   if :new.industry_risk_factor != :old.industry_risk_factor then
      insert into customer_maintenancee_log values (sys_guid(),:new.customer_id,:new.modify_user,null,null,
      'dimensional_model_credit','industry_risk_factor',null,:old.industry_risk_factor,:new.industry_risk_factor,null,:new.modify_user,sysdate);
   end if;
   if :new.industry_t != :old.industry_t then
      insert into customer_maintenancee_log values (sys_guid(),:new.customer_id,:new.modify_user,null,null,
      'dimensional_model_credit','industry_t',null,:old.industry_t,:new.industry_t,null,:new.modify_user,sysdate);
   end if;
   if :new.industry_final_value != :old.industry_final_value then
      insert into customer_maintenancee_log values (sys_guid(),:new.customer_id,:new.modify_user,null,null,
      'dimensional_model_credit','industry_final_value',null,:old.industry_final_value,:new.industry_final_value,null,:new.modify_user,sysdate);
   end if;
   if :new.six_months_overdue_value != :old.six_months_overdue_value then
      insert into customer_maintenancee_log values (sys_guid(),:new.customer_id,:new.modify_user,null,null,
      'dimensional_model_credit','six_months_overdue_value',null,:old.six_months_overdue_value,:new.six_months_overdue_value,null,:new.modify_user,sysdate);
   end if;
   if :new.six_months_overdue_weight != :old.six_months_overdue_weight then
      insert into customer_maintenancee_log values (sys_guid(),:new.customer_id,:new.modify_user,null,null,
      'dimensional_model_credit','six_months_overdue_weight',null,:old.six_months_overdue_weight,:new.six_months_overdue_weight,null,:new.modify_user,sysdate);
   end if;
   if :new.successive_late_value != :old.successive_late_value then
      insert into customer_maintenancee_log values (sys_guid(),:new.customer_id,:new.modify_user,null,null,
      'dimensional_model_credit','successive_late_value',null,:old.successive_late_value,:new.successive_late_value,null,:new.modify_user,sysdate);
   end if;
   if :new.successive_late_weight != :old.successive_late_weight then
      insert into customer_maintenancee_log values (sys_guid(),:new.customer_id,:new.modify_user,null,null,
      'dimensional_model_credit','successive_late_weight',null,:old.successive_late_weight,:new.successive_late_weight,null,:new.modify_user,sysdate);
   end if;
   if :new.single_late_value != :old.single_late_value then
      insert into customer_maintenancee_log values (sys_guid(),:new.customer_id,:new.modify_user,null,null,
      'dimensional_model_credit','single_late_value',null,:old.single_late_value,:new.single_late_value,null,:new.modify_user,sysdate);
   end if;
   if :new.single_late_weight != :old.single_late_weight then
      insert into customer_maintenancee_log values (sys_guid(),:new.customer_id,:new.modify_user,null,null,
      'dimensional_model_credit','single_late_weight',null,:old.single_late_weight,:new.single_late_weight,null,:new.modify_user,sysdate);
   end if;
   if :new.all_late_value != :old.all_late_value then
      insert into customer_maintenancee_log values (sys_guid(),:new.customer_id,:new.modify_user,null,null,
      'dimensional_model_credit','all_late_value',null,:old.all_late_value,:new.all_late_value,null,:new.modify_user,sysdate);
   end if;
   if :new.all_late_weight != :old.all_late_weight then
      insert into customer_maintenancee_log values (sys_guid(),:new.customer_id,:new.modify_user,null,null,
      'dimensional_model_credit','all_late_weight',null,:old.all_late_weight,:new.all_late_weight,null,:new.modify_user,sysdate);
   end if;
   if :new.responsible_value != :old.responsible_value then
      insert into customer_maintenancee_log values (sys_guid(),:new.customer_id,:new.modify_user,null,null,
      'dimensional_model_credit','responsible_value',null,:old.responsible_value,:new.responsible_value,null,:new.modify_user,sysdate);
   end if;
   if :new.responsible_weight != :old.responsible_weight then
      insert into customer_maintenancee_log values (sys_guid(),:new.customer_id,:new.modify_user,null,null,
      'dimensional_model_credit','responsible_weight',null,:old.responsible_weight,:new.responsible_weight,null,:new.modify_user,sysdate);
   end if;
   if :new.home_job_change_value != :old.home_job_change_value then
      insert into customer_maintenancee_log values (sys_guid(),:new.customer_id,:new.modify_user,null,null,
      'dimensional_model_credit','home_job_change_value',null,:old.home_job_change_value,:new.home_job_change_value,null,:new.modify_user,sysdate);
   end if;
   if :new.home_job_change_weight != :old.home_job_change_weight then
      insert into customer_maintenancee_log values (sys_guid(),:new.customer_id,:new.modify_user,null,null,
      'dimensional_model_credit','home_job_change_weight',null,:old.home_job_change_weight,:new.home_job_change_weight,null,:new.modify_user,sysdate);
   end if;
   if :new.six_months_succe_value != :old.six_months_succe_value then
      insert into customer_maintenancee_log values (sys_guid(),:new.customer_id,:new.modify_user,null,null,
      'dimensional_model_credit','six_months_succe_value',null,:old.six_months_succe_value,:new.six_months_succe_value,null,:new.modify_user,sysdate);
   end if;
   if :new.six_months_succe_weight != :old.six_months_succe_weight then
      insert into customer_maintenancee_log values (sys_guid(),:new.customer_id,:new.modify_user,null,null,
      'dimensional_model_credit','six_months_succe_weight',null,:old.six_months_succe_weight,:new.six_months_succe_weight,null,:new.modify_user,sysdate);
   end if;
   if :new.charater_t != :old.charater_t then
      insert into customer_maintenancee_log values (sys_guid(),:new.customer_id,:new.modify_user,null,null,
      'dimensional_model_credit','charater_t',null,:old.charater_t,:new.charater_t,null,:new.modify_user,sysdate);
   end if;
   if :new.charater_all_value != :old.charater_all_value then
      insert into customer_maintenancee_log values (sys_guid(),:new.customer_id,:new.modify_user,null,null,
      'dimensional_model_credit','charater_all_value',null,:old.charater_all_value,:new.charater_all_value,null,:new.modify_user,sysdate);
   end if;
   if :new.charater_final_value != :old.charater_final_value then
      insert into customer_maintenancee_log values (sys_guid(),:new.customer_id,:new.modify_user,null,null,
      'dimensional_model_credit','charater_final_value',null,:old.charater_final_value,:new.charater_final_value,null,:new.modify_user,sysdate);
   end if;
   if :new.denial_value != :old.denial_value then
      insert into customer_maintenancee_log values (sys_guid(),:new.customer_id,:new.modify_user,null,null,
      'dimensional_model_credit','denial_value',null,:old.denial_value,:new.denial_value,null,:new.modify_user,sysdate);
   end if;
   if :new.final_value != :old.final_value then
      insert into customer_maintenancee_log values (sys_guid(),:new.customer_id,:new.modify_user,null,null,
      'dimensional_model_credit','final_value',null,:old.final_value,:new.final_value,null,:new.modify_user,sysdate);
   end if; 
   
exception
   WHEN OTHERS THEN
        DBMS_OUTPUT.PUT_LINE(SQLCODE||'---'||SQLERRM);  

end tiger_dimensional_model_credit;


-- 影像附件 维护log触发器
create or replace trigger tiger_video_accessories
  after update on video_accessories  
  for each row
declare
  -- local variables here
begin
   if :new.application_id != :old.application_id then
      insert into customer_maintenancee_log values (sys_guid(),:new.customer_id,:new.update_user,null,null,
      'video_accessories','application_id',null,:old.application_id,:new.application_id,null,:new.update_user,sysdate);
   end if;
   if :new.server_url_path != :old.server_url_path then
      insert into customer_maintenancee_log values (sys_guid(),:new.customer_id,:new.update_user,null,null,
      'video_accessories','server_url_path',null,:old.server_url_path,:new.server_url_path,null,:new.update_user,sysdate);
   end if;
   if :new.image_type != :old.image_type then
      insert into customer_maintenancee_log values (sys_guid(),:new.customer_id,:new.update_user,null,null,
      'video_accessories','image_type',null,:old.image_type,:new.image_type,null,:new.update_user,sysdate);
   end if;
   if :new.file_path != :old.file_path then
      insert into customer_maintenancee_log values (sys_guid(),:new.customer_id,:new.update_user,null,null,
      'video_accessories','file_path',null,:old.file_path,:new.file_path,null,:new.update_user,sysdate);
   end if;
   if :new.file_name != :old.file_name then
      insert into customer_maintenancee_log values (sys_guid(),:new.customer_id,:new.update_user,null,null,
      'video_accessories','file_name',null,:old.file_name,:new.file_name,null,:new.update_user,sysdate);
   end if;
   
exception
   WHEN OTHERS THEN
        DBMS_OUTPUT.PUT_LINE(SQLCODE||'---'||SQLERRM);  
   
end tiger_video_accessories;


-- 客户职业资料维护log触发器
create or replace trigger tiger_CUSTOMER_CAREERS_INFO
  after update on customer_careers_information  
  for each row
declare
  -- local variables here
begin
   if :new.name_unit != :old.name_unit then
      insert into customer_maintenancee_log values (sys_guid(),:new.customer_id,:new.modify_user,null,null,
      'customer_careers_information','name_unit',null,:old.name_unit,:new.name_unit,null,:new.modify_user,sysdate);
   end if;
   if :new.department_name != :old.department_name then
      insert into customer_maintenancee_log values (sys_guid(),:new.customer_id,:new.modify_user,null,null,
      'customer_careers_information','department_name',null,:old.department_name,:new.department_name,null,:new.modify_user,sysdate);
   end if;
   if :new.unit_address != :old.unit_address then
      insert into customer_maintenancee_log values (sys_guid(),:new.customer_id,:new.modify_user,null,null,
      'customer_careers_information','unit_address',null,:old.unit_address,:new.unit_address,null,:new.modify_user,sysdate);
   end if;
   if :new.zip_code != :old.zip_code then
      insert into customer_maintenancee_log values (sys_guid(),:new.customer_id,:new.modify_user,null,null,
      'customer_careers_information','zip_code',null,:old.zip_code,:new.zip_code,null,:new.modify_user,sysdate);
   end if;
   if :new.unit_phone != :old.unit_phone then
      insert into customer_maintenancee_log values (sys_guid(),:new.customer_id,:new.modify_user,null,null,
      'customer_careers_information','unit_phone',null,:old.unit_phone,:new.unit_phone,null,:new.modify_user,sysdate);
   end if;
   if :new.unit_nature != :old.unit_nature then
      insert into customer_maintenancee_log values (sys_guid(),:new.customer_id,:new.modify_user,null,null,
      'customer_careers_information','unit_nature',null,:old.unit_nature,:new.unit_nature,null,:new.modify_user,sysdate);
   end if;
   if :new.industry_type != :old.industry_type then
      insert into customer_maintenancee_log values (sys_guid(),:new.customer_id,:new.modify_user,null,null,
      'customer_careers_information','industry_type',null,:old.industry_type,:new.industry_type,null,:new.modify_user,sysdate);
   end if;
   if :new.positio != :old.positio then
      insert into customer_maintenancee_log values (sys_guid(),:new.customer_id,:new.modify_user,null,null,
      'customer_careers_information','positio',null,:old.positio,:new.positio,null,:new.modify_user,sysdate);
   end if;
   if :new.title != :old.title then
      insert into customer_maintenancee_log values (sys_guid(),:new.customer_id,:new.modify_user,null,null,
      'customer_careers_information','title',null,:old.title,:new.title,null,:new.modify_user,sysdate);
   end if;
   if :new.annual_income != :old.annual_income then
      insert into customer_maintenancee_log values (sys_guid(),:new.customer_id,:new.modify_user,null,null,
      'customer_careers_information','annual_income',null,:old.annual_income,:new.annual_income,null,:new.modify_user,sysdate);
   end if;
   if :new.year_work_unit != :old.year_work_unit then
      insert into customer_maintenancee_log values (sys_guid(),:new.customer_id,:new.modify_user,null,null,
      'customer_careers_information','year_work_unit',null,:old.year_work_unit,:new.year_work_unit,null,:new.modify_user,sysdate);
   end if;
   if :new.before_name_unit != :old.before_name_unit then
      insert into customer_maintenancee_log values (sys_guid(),:new.customer_id,:new.modify_user,null,null,
      'customer_careers_information','before_name_unit',null,:old.before_name_unit,:new.before_name_unit,null,:new.modify_user,sysdate);
   end if;
   if :new.before_year_work_unit != :old.before_year_work_unit then
      insert into customer_maintenancee_log values (sys_guid(),:new.customer_id,:new.modify_user,null,null,
      'customer_careers_information','before_year_work_unit',null,:old.before_year_work_unit,:new.before_year_work_unit,null,:new.modify_user,sysdate);
   end if;
   if :new.work_time != :old.work_time then
      insert into customer_maintenancee_log values (sys_guid(),:new.customer_id,:new.modify_user,null,null,
      'customer_careers_information','work_time',null,:old.work_time,:new.work_time,null,:new.modify_user,sysdate);
   end if;
   
exception
   WHEN OTHERS THEN
        DBMS_OUTPUT.PUT_LINE(SQLCODE||'---'||SQLERRM);
   
end tiger_CUSTOMER_CAREERS_INFO;

-- 客户陌拜信息维护log触发器
create or replace trigger tiger_customer_worship_Info
  after update on customer_worship_information  
  for each row
declare
  -- local variables here
begin
   if :new.marketing_time != :old.marketing_time then
      insert into customer_maintenancee_log values (sys_guid(),:new.customer_id,:new.modify_user,null,null,
      'customer_worship_information','marketing_time',null,:old.marketing_time,:new.marketing_time,null,:new.modify_user,sysdate);
   end if;
   if :new.engaged_industry != :old.engaged_industry then
      insert into customer_maintenancee_log values (sys_guid(),:new.customer_id,:new.modify_user,null,null,
      'customer_worship_information','engaged_industry',null,:old.engaged_industry,:new.engaged_industry,null,:new.modify_user,sysdate);
   end if;
   if :new.contact_phone_number != :old.contact_phone_number then
      insert into customer_maintenancee_log values (sys_guid(),:new.customer_id,:new.modify_user,null,null,
      'customer_worship_information','contact_phone_number',null,:old.contact_phone_number,:new.contact_phone_number,null,:new.modify_user,sysdate);
   end if;
   if :new.demand_credit != :old.demand_credit then
      insert into customer_maintenancee_log values (sys_guid(),:new.customer_id,:new.modify_user,null,null,
      'customer_worship_information','demand_credit',null,to_char(:old.demand_credit),to_char(:new.demand_credit),null,:new.modify_user,sysdate);
   end if;
   if :new.amount_numberent != :old.amount_numberent then
      insert into customer_maintenancee_log values (sys_guid(),:new.customer_id,:new.modify_user,null,null,
      'customer_worship_information','amount_numberent',null,:old.amount_numberent,:new.amount_numberent,null,:new.modify_user,sysdate);
   end if;
   if :new.numberention_deadline != :old.numberention_deadline then
      insert into customer_maintenancee_log values (sys_guid(),:new.customer_id,:new.modify_user,null,null,
      'customer_worship_information','numberention_deadline',null,:old.numberention_deadline,:new.numberention_deadline,null,:new.modify_user,sysdate);
   end if;
   if :new.numbererest_repayment_way != :old.numbererest_repayment_way then
      insert into customer_maintenancee_log values (sys_guid(),:new.customer_id,:new.modify_user,null,null,
      'customer_worship_information','numbererest_repayment_way',null,:old.numbererest_repayment_way,:new.numbererest_repayment_way,null,:new.modify_user,sysdate);
   end if;
   if :new.area != :old.area then
      insert into customer_maintenancee_log values (sys_guid(),:new.customer_id,:new.modify_user,null,null,
      'customer_worship_information','area',null,:old.area,:new.area,null,:new.modify_user,sysdate);
   end if;
   if :new.address != :old.address then
      insert into customer_maintenancee_log values (sys_guid(),:new.customer_id,:new.modify_user,null,null,
      'customer_worship_information','address',null,:old.address,:new.address,null,:new.modify_user,sysdate);
   end if;
   if :new.suggested_marketing_again != :old.suggested_marketing_again then
      insert into customer_maintenancee_log values (sys_guid(),:new.customer_id,:new.modify_user,null,null,
      'customer_worship_information','suggested_marketing_again',null,to_char(:old.suggested_marketing_again),to_char(:new.suggested_marketing_again),null,:new.modify_user,sysdate);
   end if;
   if :new.suggest_marketing_time_again != :old.suggest_marketing_time_again then
      insert into customer_maintenancee_log values (sys_guid(),:new.customer_id,:new.modify_user,null,null,
      'customer_worship_information','suggest_marketing_time_again',null,:old.suggest_marketing_time_again,:new.suggest_marketing_time_again,null,:new.modify_user,sysdate);
   end if;
   if :new.potential_customer != :old.potential_customer then
      insert into customer_maintenancee_log values (sys_guid(),:new.customer_id,:new.modify_user,null,null,
      'customer_worship_information','potential_customer',null,to_char(:old.potential_customer),to_char(:new.potential_customer),null,:new.modify_user,sysdate);
   end if;
   
exception
   WHEN OTHERS THEN
        DBMS_OUTPUT.PUT_LINE(SQLCODE||'---'||SQLERRM); 

end tiger_customer_worship_Info;