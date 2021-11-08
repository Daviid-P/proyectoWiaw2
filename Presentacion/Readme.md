# Presentación creada en MarkDown pasada a HTML

La presentación es el fichero presentation.html

## Para generar de nuevo la presentación:

### Ejecutar en este directorio:

```
pandoc -t html5 --template=template-revealjs.html \
    --standalone --section-divs \
  --variable theme="sky" \
  --variable transition="linear" \
  presentation.md -o presentation.html
```

