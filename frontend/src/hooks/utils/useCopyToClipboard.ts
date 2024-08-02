import { useEffect, useState } from 'react';

interface useCopyToClipboardProps {
  url: string;
}

export default function useCopyToClipboard({ url }: useCopyToClipboardProps) {
  const [isCopied, setIsCopied] = useState(false);

  const copyToClipboard = () => {
    if (navigator.clipboard) {
      navigator.clipboard
        .writeText(url)
        .then(() => {
          // TODO: toast 모달로 변경
          window.alert('링크가 복사되었습니다.');
          setIsCopied(true);
        })
        .catch(() => {
          window.alert('링크 복사에 실패했습니다.');
        });
    } else {
      window.alert('해당 브라우저에서는 지원하지 않습니다.');
    }
  };

  useEffect(() => {
    let timer: NodeJS.Timeout;

    if (isCopied) {
      timer = setTimeout(() => {
        setIsCopied(false);
      }, 2000);
    }

    return () => clearTimeout(timer);
  }, [isCopied]);

  return { isCopied, copyToClipboard };
}
