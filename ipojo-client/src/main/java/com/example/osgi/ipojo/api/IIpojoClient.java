package com.example.osgi.ipojo.api;

import com.example.osgi.blueprint.api.IDateService;
import com.example.osgi.blueprint.api.IReverserService;

/**
 * public interface to client tool, implements service clases
 * from service bundle
 * 
 * @see com.example.osgi.blueprint.api.IDateService
 * @see com.example.osgi.blueprint.api.IReverserService
 * 
 * @author mike
 *
 */
public interface IIpojoClient extends IDateService,IReverserService
{
}
