     $("#id_save_button").click(function() {
        if ($($formName).valid()) {
          var formjson = $($formName).serialize();
          $.get($($formName).attr("action"), formjson, function(data, textStatus, jqXhr) {
            if (data.success) {
              #if ($request.getParameter('callBackUrl').indexOf('?') != -1)
                navigateTo("${request.getParameter('callBackUrl').replaceAll('\?.*','')}" + "?recordId=" + data.recordId);
              #else
                navigateTo("${request.getParameter('callBackUrl')}" + "?recordId=" + data.recordId);
              #end
            }
          });
        }
      });
