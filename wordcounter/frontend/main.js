import { app, BrowserWindow } from 'electron'

// var kill = require('tree-kill');

const createWindow = () => {
    const win = new BrowserWindow({
        width: 800,
        height: 600,
        webPreferences: {
            nodeIntegration: true
        }

    })

    // win.loadFile('index.html')
    win.loadURL('http://localhost:8080');
}

// var jarPath = app.getAppPath() + '\\WordCounter.jar';
// var child = require('child_process').spawn(
//     'java', ['-jar', jarPath, '']
// );

app.whenReady().then(() => {
    createWindow()

    app.on('activate', () => {
        if (BrowserWindow.getAllWindows().length === 0) createWindow()
    })
})

app.on('window-all-closed', () => {
    if (process.platform !== 'darwin') {
        // kill(child.pid);
        app.quit()
    }
})