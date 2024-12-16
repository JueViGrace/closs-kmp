package org.closs.core.types.config

import org.closs.core.shared.types.Constants
import org.closs.core.shared.types.config.ConfigDto
import org.closs.core.shared.types.config.CreateConfigDto
import org.closs.core.types.aliases.DbConfig

fun DbConfig.dbConfigToDto(): ConfigDto = ConfigDto(
    idConfig = cnfg_idconfig,
    clase = cnfg_clase,
    tipo = cnfg_tipo,
    valNum = cnfg_valnum,
    valSino = cnfg_valsino.toInt(),
    valTxt = cnfg_valtxt,
    lenTxt = cnfg_lentxt.toInt(),
    valFch = cnfg_valfch,
    activa = cnfg_activa.toInt(),
    etiq = cnfg_etiq,
    ttip = cnfg_ttip,
    username = username,
    createdAt = created_at,
    updatedAt = updated_at
)

fun CreateConfigDto.toDbConfig(): DbConfig = DbConfig(
    cnfg_idconfig = idConfig,
    cnfg_clase = clase,
    cnfg_tipo = tipo,
    cnfg_valnum = valNum,
    cnfg_valsino = valSino.toLong(),
    cnfg_valtxt = valTxt,
    cnfg_lentxt = lenTxt.toLong(),
    cnfg_valfch = valFch,
    cnfg_activa = activa.toLong(),
    cnfg_etiq = etiq,
    cnfg_ttip = ttip,
    username = username,
    created_at = Constants.currentTime,
    updated_at = Constants.currentTime,
    deleted_at = null
)
