 /**
	 * 预览文件
	 * @param savePath	保存目录
	 * @param name		文件原名
	 * @param nowName	保存时的UUID 不包含后缀
	 * @param ext		文件后缀
	 * @param request
	 * @param response
	 * @return
	 */
    public static String inline(String savePath,String name,String uuid,String ext,HttpServletRequest request,HttpServletResponse response){
		response.addHeader("Access-Control-Allow-Origin", "*");
    	OutputStream toClient=null;
    	try{
    		String path=savePath+"/"+uuid+"."+ext;
//			String path=savePath+"/"+name;
    		//判断此文件是否为pdf
			File file=null;
			if(ext.equals("pdf")){
				//如果是pdf
				file = new File(path);
			}else{
				//如果不是，转化为pdf
				file =FilePreviewUtil.openOfficeToPdf(path);
			}
	        if(!file.exists()){
	        	//不存在
				request.setAttribute("name", name);
	        	return "download_error";//返回下载文件不存在
			}
//	        if(!inOnLineExt(ext)){
//	        	 response.setContentType("application/octet-stream");
//	        }
	        // 根据不同浏览器 设置response的Header
//	        String userAgent = request.getHeader("User-Agent").toLowerCase();
//
//	        if(userAgent.indexOf("msie")!=-1||userAgent.indexOf("trident")!=-1){
//	        	//ie浏览器
//	        	//System.out.println("ie浏览器");
//	        	response.addHeader("Content-Disposition", "inline;filename=" + URLEncoder.encode(name,"utf-8"));
//
//	        }else{
//	        	response.addHeader("Content-Disposition", "inline;filename=" + new String(name.getBytes("utf-8"),"ISO8859-1"));
//	        }

//	        response.addHeader("Content-Length", ""+file.length());
//	        //以流的形式下载文件
//	        InputStream fis = new BufferedInputStream(new FileInputStream(path));
//	        byte[] buffer = new byte[fis.available()];
//	        fis.read(buffer);
//	        fis.close();
//	        toClient = new BufferedOutputStream(response.getOutputStream());
//	        toClient.write(buffer);
//	        toClient.flush();
			//把转换后的pdf文件响应到浏览器上面
			FileInputStream fileInputStream = new FileInputStream(file);
			BufferedInputStream br = new BufferedInputStream(fileInputStream);
			byte[] buf = new byte[1024];
			int length;
			// 清除首部的空白行。非常重要
			response.reset();
			//设置调用浏览器嵌入pdf模块来处理相应的数据。
			response.setContentType("application/pdf");
			response.setHeader("Content-Disposition","inline; filename=" + URLEncoder.encode(file.getName(), "UTF-8"));
			OutputStream out = response.getOutputStream();
			//写文件
			while ((length = br.read(buf)) != -1){
				out.write(buf, 0, length);
			}
			br.close();
			out.close();
	        return null;
	      }catch (Exception e) {
	    	  e.printStackTrace();
	    	  response.reset();
	    	  return "exception";//返回异常页面
	      }finally{
				if(toClient!=null){
		           	 try {
		           		toClient.close();
					  } catch (IOException e) {
							e.printStackTrace();
						}
		            }
				}
    }
