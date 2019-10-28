/*
    Virtual Power Thing
*/


metadata {
    definition (name: "Virtual Power", namespace: "asj", author: "asj") {
        capability "Configuration"
        capability "Refresh"
        capability "PowerMeter"
        capability "EnergyMeter"
        capability "Sensor"
        capability "Outlet"
        capability "Switch"

    }

    preferences {
        //standard logging options
        input name: "powerValue", type: "number", title: "Power Value", defaultValue: 0
        input name: "logEnable", type: "bool", title: "Enable debug logging", defaultValue: true
        input name: "txtEnable", type: "bool", title: "Enable descriptionText logging", defaultValue: true
    }
}

def logsOff(){
    log.warn "debug logging disabled..."
    device.updateSetting("logEnable",[value:"false",type:"bool"])
}

//event methods
private sendPowerResult(){
    def value = powerValue
    def name = "power"
    def unit = "W"
    def descriptionText = "${device.displayName} ${name} is ${value}${unit}"
    if (txtEnable) log.info "${descriptionText}"
    sendEvent(name: name,value: value,descriptionText: descriptionText,unit: unit)
}


//capability and device methods
def off() {
}

def on() {
}

def refresh() {
    log.debug "Refresh"
    sendPowerResult()
}

def configure() {
    log.debug "Configuring Reporting and Bindings."
    runIn(1800,logsOff)
}

def updated() {
    sendPowerResult()
}


