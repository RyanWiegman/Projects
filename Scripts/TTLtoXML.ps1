Write-Host "Hello, World!"

$folderPath = "C:\workspace\Utilities\PFS_2"
$fileType = "*.TTL"

$xml = New-Object System.Xml.XmlDocument
$root = $xml.CreateElement("Folders")
$xml.AppendChild($root) | Out-Null

$subFolder = Get-ChildItem -Path $folderPath -Directory
foreach($folder in $subFolder) {
  $folderElement = $xml.CreateElement("Folder")
  $folderElement.SetAttribute("Name", $folder.name)
  
  $files = Get-ChildItem -Path $folderPath -Filter $fileType -File -Recurse
  foreach($file in $files) {
    try {
      $csvData = Import-Csv -Path $file.FullName -Header "Key", "Value"
  
      $fileElement = $xml.CreateElement("File")
      $fileElement.SetAttribute("File", $file.Name)
  
      foreach ($row in $csvData) {
        $keyValueElement = $xml.CreateElement("KeyValuePair")
        $keyValueElement.SetAttribute("Key", $row.Key)
        $keyValueElement.SetAttribute("Value", $row.Value)
       
        $fileElement.AppendChild($keyValueElement)
      }
  
      $folderElement.AppendChild($fileElement) | Out-Null
    }
    catch {
      Write-Error "Error Reading file '$($file.FullName)': $($_.Exception.Message)"
    }
  
    $root.AppendChild($folderElement) | Out-Null
  }
}


$xml.Save("output.xml")
Write-Host "XML file created successfully: output.xml"
