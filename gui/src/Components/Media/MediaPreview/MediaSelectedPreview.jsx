import React, { useMemo, useEffect, useState } from 'react';

const MediaSelectedPreview = ({ mediaFileObjects, onRemove, styleContainer }) => {

    const [urlsReady, setUrlsReady] = useState(false);

    // Create stable file preview URLs that don't change on every re-render
    const filePreviewUrls = useMemo(() => {
        const urls = Array.from(mediaFileObjects || []).map(file => ({
            url: URL.createObjectURL(file),
            type: file.type,
            name: file.name
        }));

        if (urls.length > 0) {
            // Mark URLs as ready after they're created
            setUrlsReady(true);
        }

        return urls;
    }, [mediaFileObjects]);

    // Force a re-render after URLs are created
    useEffect(() => {
        if (filePreviewUrls.length > 0 && !urlsReady) {
            // Small timeout to ensure browser has time to process the URLs
            const timer = setTimeout(() => {
                setUrlsReady(true);
            }, 50);
            return () => clearTimeout(timer);
        }
    }, [filePreviewUrls, urlsReady]);

    return (
        <div className={styleContainer}>
            {filePreviewUrls.map((filePreview, index) => (
                <div key={index} className="relative w-35 h-35 overflow-hidden rounded-md">
                    {filePreview.type.startsWith('image/') ? (
                        // If image exist
                        <img
                            className="w-full h-full object-cover rounded-md"
                            src={filePreview.url}
                            alt={`Preview ${index}`}
                        />
                    ) : filePreview.type.startsWith('video/') ? (
                        // Else if video exist
                        <video controls className="w-full h-full object-cover rounded-md">
                            <source
                                src={filePreview.url}
                                type={filePreview.type}
                            />
                            Your browser does not support the video tag.
                        </video>
                    ) : (
                        // Else unknown file ?
                        <div>{filePreview.name}</div>
                    )}
                    {onRemove && (
                        <button
                            className="absolute top-1 right-1 w-6 h-6 flex items-center justify-center
                            bg-red-500 text-white rounded-full text-xs cursor-pointer
                            hover:bg-red-600 transition-colors"
                            onClick={() => onRemove(index)}
                        >
                            ×
                        </button>
                    )}
                </div>
            ))}
        </div>
    );
};

export default MediaSelectedPreview;
