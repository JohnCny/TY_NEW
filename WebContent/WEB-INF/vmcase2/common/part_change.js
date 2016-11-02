     $("#id_save_button").click(function() {
        if ($($formName).valid()) {
          var formjson = $($formName).serialize();
          $.get($($formName).attr("action"), formjson, function(data, textStatus, jqXhr) {
            if (data.success) {
              #set ($callBackUrl = $request.getParameter('callBackUrl').replaceAll("recordId=.*",""))
              #if ($request.getParameter('callBackUrl').indexOf('?') != -1)
                navigateTo("${callBackUrl}" + "&recordId=" + data.recordId);
              #else
                navigateTo("${callBackUrl}" + "?recordId=" + data.recordId);
              #end
            }
          });
        }
      });
